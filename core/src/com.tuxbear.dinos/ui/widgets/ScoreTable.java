package com.tuxbear.dinos.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.domain.user.*;

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

    public void renderMissionScores(int roundNumberForCompletedMission) {
        Mission missionToShowResultsFor = game.getMission(roundNumberForCompletedMission);

        clear();

        add("<player>").expandX().center();
        add("<moves>").expandX().center();
        add("<time>").expandX().center();
        add("<round>").expandX().center();
        add("<total>").expandX().center();

        row().height(10);

        for(String player : game.getPlayers()) {

            row();
            MissionResult roundResults = game.getMissionResultForPlayer(player, missionToShowResultsFor.getId());

            add(new PlayerWidget(player, skin)).expandX().center();

            if (roundResults != null) {
                add(String.format("%s", roundResults.getMoveSequence().getMoves().size())).expandX().center();
                add(String.format("%s", roundResults.getMillisecondsSpent())).expandX().center();
                add(String.format("%s", roundResults.getScore())).expandX().center();
            } else {
                add("-").expandX().center();
                add("-").expandX().center();
                add("-").expandX().center();
            }

            long totalScoreForPlayer = game.getTotalScoreForPlayer(player);
            add(String.format("%s", totalScoreForPlayer)).expandX().center();
        }

        invalidate();
    }
}
