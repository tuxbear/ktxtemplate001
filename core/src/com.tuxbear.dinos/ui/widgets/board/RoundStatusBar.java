package com.tuxbear.dinos.ui.widgets.board;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.tuxbear.dinos.domain.events.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.services.events.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 08/12/13 Time: 16:10 To change this template use File | Settings | File
 * Templates.
 */
public class RoundStatusBar extends Table implements GameEventListener{
    EventBus eventBus = IoC.resolve(EventBus.class);

    private int numberOfMoves;
    private float elapsed;

    private final Label movesLabel;

    private final Label timeLabel;

    private boolean isRoundActive;

    private final Image missionDinoImage;


    public RoundStatusBar(Skin skin) {
        super(skin);
        eventBus.subscribe(this, DinoMovedEvent.class, MissionStartingEvent.class, MissionAccomplishedEvent.class);

        missionDinoImage = new Image();

        movesLabel = new Label("0", skin);
        timeLabel = new Label("00:00", skin);
        add(missionDinoImage);
        add(movesLabel);
        add(timeLabel);
    }

    @Override
    public void act(float delta) {
        if (isRoundActive) {
            elapsed += delta;
            int elapsedSeconds = (int)Math.floor(elapsed);
            int elapsedMinutes = elapsedSeconds / 60;
            elapsedSeconds = elapsedSeconds % 60;
            movesLabel.setText("" + numberOfMoves);

            timeLabel.setText( String.format("%02d:%02d", elapsedMinutes, elapsedSeconds));
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
    }

    private void onMissionAccomplished() {
        isRoundActive = false;
    }

    private void onStartMission(Mission mission) {
        missionDinoImage.setDrawable(new SpriteDrawable(new Sprite(DinoActor.getDinoTextureFromNumber(mission.getPieceNumber()))));
        numberOfMoves = 0;
        elapsed = 0;
        isRoundActive = true;
    }
}
