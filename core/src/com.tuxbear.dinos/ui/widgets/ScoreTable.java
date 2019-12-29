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
        add(value).pad(20);
    }

    private void addTimeCell(String value) {
        add(value).pad(20);

    }

    public void renderMissionScores(int roundNumberForCompletedMission) {

        int missionToShow = Math.min(roundNumberForCompletedMission, game.getNumberOfMissions()-1);

        Mission missionToShowResultsFor = game.getMission(missionToShow);
        clear();

        add("player");
        add("round").colspan(2);
        add("total").colspan(2);
        row();

        add(" ");

        addMoveCell("Moves");
        addTimeCell("Time");

        addMoveCell("Moves");
        addTimeCell("Time");

        for(String player : game.getPlayers()) {

            row();
            MissionResult roundResults = game.getMissionResultForPlayer(player, missionToShowResultsFor.getId());

            add(new PlayerWidget(player, skin)).expandX().center();

            if (roundResults != null) {

                int elapsedRoundSeconds = (int)Math.floor(roundResults.getTimeSpent() / 1000);
                int elapsedRoundMinutes = elapsedRoundSeconds / 60;
                elapsedRoundSeconds = elapsedRoundSeconds % 60;

                addMoveCell(String.format("%s", roundResults.getNumberOfMoves()));
                addTimeCell(String.format("%s", String.format("%02d:%02d", elapsedRoundMinutes, elapsedRoundSeconds)));


                int elapsedGameSeconds = (int)Math.floor(game.getTotalTimeSpent(player) / 1000);
                int elapsedGameMinutes = elapsedGameSeconds / 60;
                elapsedGameSeconds = elapsedGameSeconds % 60;

                addMoveCell(String.format("%s", game.getTotalMovesForPlayer(player)));
                addTimeCell(String.format("%s", String.format("%02d:%02d", elapsedGameMinutes, elapsedGameSeconds)));
            } else {
                addMoveCell("-");
                addTimeCell("-");
                addMoveCell("-");
                addTimeCell("-");
            }
        }

        invalidate();
    }
}
