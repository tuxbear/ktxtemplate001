package com.tuxbear.dinos.ui.widgets.board;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.tuxbear.dinos.domain.events.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.services.events.*;

import java.time.Instant;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 08/12/13 Time: 16:10 To change this template use File | Settings | File
 * Templates.
 */
public class RoundStatusBar extends Table implements GameEventListener{
    private final Label totalScoreLabel;
    EventBus eventBus = IoC.resolve(EventBus.class);

    private int numberOfMoves;

    private final Label moveScoreLabel;

    private final Label firstMoveScoreLabel;

    private boolean isRoundActive;
    private Long firstMoveTime;

    private final Image missionDinoImage;
    private ScoreService scoreService = new ArcadeGameScoreService();
    private long missionStartTime;


    public RoundStatusBar(Skin skin) {
        super(skin);
        eventBus.subscribe(this, DinoMovedEvent.class, MissionStartingEvent.class, MissionAccomplishedEvent.class);

        missionDinoImage = new Image();

        moveScoreLabel = new Label("0", skin);
        firstMoveScoreLabel = new Label("0", skin);
        totalScoreLabel = new Label("0", skin);

        add(missionDinoImage).padRight(50);
        add(firstMoveScoreLabel).width(100);
        add(" + ").width(50);
        add(moveScoreLabel).width(100);
        add(" = ").width(50);
        add(totalScoreLabel).width(100);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isRoundActive) {
            int moveScore = scoreService.moveScore(numberOfMoves);
            moveScoreLabel.setText(moveScore);
            long timeToUse = firstMoveTime == 0 ? Instant.now().toEpochMilli() - missionStartTime : firstMoveTime;
            int firstMoverScore = scoreService.getFirstMoverScore(timeToUse);
            firstMoveScoreLabel.setText(firstMoverScore);
            totalScoreLabel.setText(moveScore + firstMoverScore);
            invalidateHierarchy();
        }
    }

    @Override
    public void processEvent(GameEvent event) {
        Class<? extends GameEvent> eventType = event.getClass();
        if(eventType == MissionStartingEvent.class) {
            onStartMission(((MissionStartingEvent) event).getMission());
        } else if (eventType == MissionAccomplishedEvent.class) {
            onMissionAccomplished();
        } else if (eventType == DinoMovedEvent.class) {
            onDinoMoved();
        }
    }

    private void onDinoMoved() {
        numberOfMoves++;
        firstMoveTime = Instant.now().toEpochMilli() - missionStartTime;
    }

    private void onMissionAccomplished() {
        isRoundActive = false;
    }

    private void onStartMission(Mission mission) {
        missionDinoImage.setDrawable(new SpriteDrawable(new Sprite(DinoActor.getDinoTextureFromNumber(mission.getPieceNumber()))));
        numberOfMoves = 0;
        firstMoveTime = 0l;
        missionStartTime = Instant.now().toEpochMilli();
        isRoundActive = true;
    }
}
