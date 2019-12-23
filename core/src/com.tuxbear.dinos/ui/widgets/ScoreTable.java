package com.tuxbear.dinos.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.tuxbear.dinos.domain.game.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 03/01/14 Time: 17:45 To change this template use File | Settings | File
 * Templates.
 */
public class ScoreTable extends Table {

    private MultiplayerGame game;

    private Skin skin;

    public ScoreTable(MultiplayerGame game, Skin skin, int roundNumberForCompletedMission) {
        super(skin);
        this.game = game;
        this.skin = skin;
        center();
        renderMissionScores(roundNumberForCompletedMission);
    }

    private void addMoveCell(String value) {

    }


    private void addTimeCell(Long value) {

    }

    public void renderMissionScores(int roundNumberForCompletedMission) {
        Mission missionToShowResultsFor = game.getMission(roundNumberForCompletedMission);

        clear();

        add("player").expandX().center();
        add("round").expandX().center();
        add("total").expandX().center();
        row().height(100);

        add("moves").expandX().right();
        add("time").expandX().right();

        add("moves").expandX().right();
        add("time").expandX().right();


        for(String player : game.getPlayers()) {

            row();
            MissionResult roundResults = game.getMissionResultForPlayer(player, missionToShowResultsFor.getId());

            add(new PlayerWidget(player, skin)).expandX().center();

            if (roundResults != null) {
                add(String.format("%s", roundResults.getNumberOfMoves())).expandX().right();
                add(String.format("%s", roundResults.getTimeSpent())).expandX().right();

                add(String.format("%s", game.getTotalMovesForPlayer(player))).expandX().right();
                add(String.format("%s", game.getTotalTimeSpent(player))).expandX().right();
            } else {
                add("-").expandX().right();
                add("-").expandX().right();
                add("-").expandX().right();
                add("-").expandX().right();
            }
        }

        invalidate();
    }
}
