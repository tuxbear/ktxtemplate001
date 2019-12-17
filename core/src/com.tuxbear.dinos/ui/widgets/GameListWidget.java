package com.tuxbear.dinos.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.domain.user.*;
import com.tuxbear.dinos.services.*;

import java.io.IOException;
import java.util.List;

/**
 * User: tuxbear Date: 19/01/14 Time: 11:30
 */
public class GameListWidget extends Table {


    private final GameSelectedListener gameSelectedListener;

    private PlayerService playerService = IoC.resolve(PlayerService.class);
    private Logger logger = IoC.resolve(Logger.class);
    private List<MultiplayerGame> games;

    public GameListWidget(Skin skin, GameSelectedListener gameSelectedListener) {
        super(skin);
        this.gameSelectedListener = gameSelectedListener;
    }

    public void renderGameList() throws IOException {

        clear();

        Player currentPlayer = playerService.getCurrentPlayer();

        add("Rank").center();
        add("Opponents").center();
        add("Mission").center();
        add("Status").center();

        for(final MultiplayerGame game : games) {
            row();
            long score = game.getTotalScoreForPlayer(currentPlayer.getUsername());
            int rank = game.getPlayerRank(currentPlayer.getUsername());
            int currentMissionNumber = game.getCurrentMissionNumber();
            int numberOfMissions = game.getNumberOfMissions();
            LocalGameState status = game.getLocalGameState(currentPlayer.getUsername());

            add(String.format("#%s \n %sp", rank, score)).center();
            add(String.format("vs %s", game.getOpponentString(currentPlayer.getUsername()))).center();
            add(String.format("%s / %s", currentMissionNumber, numberOfMissions)).center();
            add(String.format("%s", status.prettyString())).center();
        }

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int clickedRow = getRow(y);
                int selectedGameIndex = clickedRow - 1;
                if (gameSelectedListener != null && games != null && selectedGameIndex > -1 && selectedGameIndex < games.size()) {
                    MultiplayerGame selectedGame = games.get(selectedGameIndex);
                    try {
                        gameSelectedListener.onGameSelected(selectedGame);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int rowNum = getRow(y);
                setBackgroundForCellsInRow(rowNum);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                int rowNum = getRow(y);
                setBackgroundForCellsInRow(rowNum);
                super.touchUp(event, x, y, pointer, button);
            }


        });
    }

    private void setBackgroundForCellsInRow(int rowNum) {
        for(Cell cell : getCells()) {
            if ( cell.getRow() == rowNum ) {
                // set background color
            }
        }
    }

    public void setGameList(List<MultiplayerGame> gameList) throws IOException {
        games = gameList;
        renderGameList();
    }


    public interface GameSelectedListener {
        void onGameSelected(MultiplayerGame game) throws IOException;
    }

}
