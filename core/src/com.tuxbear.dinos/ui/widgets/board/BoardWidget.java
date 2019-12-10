package com.tuxbear.dinos.ui.widgets.board;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.tuxbear.dinos.domain.events.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.services.events.*;

import java.util.*;
import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 02/12/13 Time: 18:27 To change this template use File | Settings | File
 * Templates.
 */
public class BoardWidget extends WidgetGroup {

    private PlayerService playerService = IoC.resolve(PlayerService.class);
    private final MultiplayerGame game;
    EventBus eventBus = IoC.resolve(EventBus.class);

    List<DinoActor> dinoActors;
    Map<Integer, DinoActor> dinoByNumber;
    MoveSequence currentMoveSequence;

    List<Tile> tiles;

    MissionActor currentMissionActor;

    float currentMissionTimeSpent = 0.0f;
    int currentMissionMovesMade = 0;
    boolean isSolvingMission = false;

    public float tileSize;

    private boolean dinoMoving;

    private long walkingSoundId;
    private static Sound walkingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/walking.mp3"));
    private static Sound winSound = Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3"));
    private Board board;
    private Mission currentMission;

    public BoardWidget(MultiplayerGame dinoGame, float tileSize) {
        this.board = dinoGame.getBoard();
        this.game = dinoGame;
        int numberOfDinos = dinoGame.getInitialPiecePositions().size();
        int rows = board.getRows();
        int columns = board.getColumns();
        this.tileSize = tileSize;

        tiles = new ArrayList<>(rows * columns);
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Tile tile = new Tile(x, y, tileSize, this);
                tiles.add(tile);
                this.addActor(tile);
            }
        }

        for (Wall w : board.getWalls()) {
            this.addActor( new WallActor(w, this, tileSize / rows, tileSize));
        }

        currentMissionActor = new MissionActor(this, BoardPosition.OFF_BOARD);

        addActor(currentMissionActor);

        dinoActors = new ArrayList<>(numberOfDinos);
        dinoByNumber = new HashMap<>(numberOfDinos);
        for (int i = 0; i < numberOfDinos; i++) {
            DinoActor dinoActor = DinoActor.createFromPieceNumber(i, tileSize, this);
            dinoActors.add(dinoActor);
            dinoByNumber.put(i, dinoActor);
            this.addActor(dinoActor);
        }

        this.setTouchable(Touchable.enabled);
        resetDinoPositions();
    }

    public void checkAndStartNextMission() {
        restartCurrentMission();
        eventBus.publishEvent(new MissionStartingEvent(currentMission));
    }

    private void restartCurrentMission() {
        resetDinoPositions();
        currentMissionMovesMade = 0;
        currentMissionTimeSpent = 0;
        currentMoveSequence = new MoveSequence();
        isSolvingMission = true;
        currentMission = game.getCurrentMission();
        currentMissionActor.setBoardPosition(currentMission.getPosition());
    }

    private void resetDinoPositions() {
        for ( Object dinoNumber : game.getInitialPiecePositions().keySet()) {
            Integer dinoNumberint = Integer.parseInt(dinoNumber.toString());
            DinoActor dinoActor = getDinoByNumber(dinoNumberint);
            BoardPosition initPosition = game.getInitialPiecePositions().get(dinoNumber);
            dinoActor.setBoardPosition(initPosition);
            dinoActor.updatePositionFromBoardPosition();
        }
    }

    public void moveDino(final DinoActor dinoActor, Direction direction) {

        List<BoardPosition> positionsOccupiedByDinos = getDinoPositions();
        final BoardPosition nextPos = board.getPositionInDirection(dinoActor.getBoardPosition(), direction, positionsOccupiedByDinos);
        Tile targetTile = getTileAt(nextPos);
        if (nextPos.equals(dinoActor.getBoardPosition()) || isDinoMoving()) {
            return;
        }

        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition(targetTile.getDrawX(), targetTile.getDrawY());
        moveAction.setDuration(dinoActor.getBoardPosition().getDistanceTo(nextPos) * dinoActor.getSpeed());
        moveAction.setInterpolation(Interpolation.pow2Out);

        Move thisMove = new Move();
        thisMove.setDirection(direction);
        thisMove.setPieceNumber(dinoActor.getPieceNumber());
        currentMoveSequence.getMoves().add(thisMove);

        setDinoMoving(true);
        currentMissionMovesMade++;
        eventBus.publishEvent(new DinoMovedEvent());

        walkingSoundId = walkingSound.loop(SettingsService.EFFECTS_VOLUME);
        walkingSound.setLooping(walkingSoundId, true);
        dinoActor.addAction(sequence(moveAction, run(new Runnable() {
            public void run() {
                dinoActor.setBoardPosition(nextPos);
                walkingSound.stop(walkingSoundId);
                setDinoMoving(false);
                checkForWin();
            }
        })));
    }

    private List<BoardPosition> getDinoPositions() {
        List<BoardPosition> positions = new ArrayList<>();
        for(DinoActor d: dinoActors) {
            positions.add(d.getBoardPosition());
        }
        return positions;
    }

    private Tile getTileAt(BoardPosition nextPos) {
        for(Tile tile : tiles) {
            if (tile.getBoardPosition().equals(nextPos)){
                return tile;
            }
        }

        throw new IllegalArgumentException();
    }

    private void checkForWin() {
        if (getDinoByNumber(currentMission.getPieceNumber()).getBoardPosition().equals(currentMission.getPosition())) {
            winSound.play(SettingsService.EFFECTS_VOLUME);
            isSolvingMission = false;
            int timeSpentRoundedDown = (int) Math.floor(currentMissionTimeSpent);
            int score = IoC.resolve(ScoreService.class).getScore(currentMissionMovesMade, timeSpentRoundedDown, 6);

            MissionResult result = new MissionResult();
            result.setGameId(game.getId());
            result.setNumberOfMoves(currentMissionMovesMade);
            result.setMillisecondsSpent(timeSpentRoundedDown);
            result.setScore(score);
            result.setMissionId(currentMission.getId());
            result.setMoveSequence(currentMoveSequence);

            result.setPlayerId(playerService.getCurrentPlayer().getId());
            game.reportResultForCurrentRound(result);
            eventBus.publishEvent(new MissionAccomplishedEvent(result));
        }
    }


    @Override
    public void act(float deltaTime) {
        if (isSolvingMission) {
            currentMissionTimeSpent += deltaTime;
        }
        super.act(deltaTime);
    }

    private DinoActor getDinoByNumber(int dinoNumber) {
        return dinoByNumber.get(dinoNumber);
    }


    public void setDinoMoving(boolean dinoMoving) {
        this.dinoMoving = dinoMoving;
    }

    public boolean isDinoMoving() {
        return dinoMoving;
    }

    public float getMinWidth () {
        return getPrefWidth();
    }

    public float getMinHeight () {
        return getPrefHeight();
    }

    public float getPrefWidth () {
        return board.getColumns() * tileSize;
    }

    public float getPrefHeight () {
        return board.getRows() * tileSize;
    }

    public float getMaxWidth () {
        return getPrefWidth();
    }

    public float getMaxHeight () {
        return getPrefHeight();
    }

}
