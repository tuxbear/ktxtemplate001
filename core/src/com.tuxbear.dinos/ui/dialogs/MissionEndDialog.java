package com.tuxbear.dinos.ui.dialogs;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.PlayerService;
import com.tuxbear.dinos.ui.widgets.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 03/01/14 Time: 16:21 To change this template use File | Settings | File
 * Templates.
 */
public class MissionEndDialog extends AbstractCallbackDialog {

    private final ScoreTable scoreTable;

    private final int initialMissionNumberToShow;

    PlayerService playerService = IoC.resolve(PlayerService.class);

    private int missionNumberToShow;

    private final Button prevMissionButton;

    private final Button nextMissionButton;

    public MissionEndDialog(MultiplayerGame game, Skin skin, int roundNumberToShow) {
        super("Food found!", skin);
        this.initialMissionNumberToShow = roundNumberToShow;
        this.missionNumberToShow = roundNumberToShow;

        Table table = getContentTable();

        prevMissionButton = new TextButton("<<", skin);
        nextMissionButton = new TextButton(">>", skin);

        prevMissionButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goOneMissionBack();
            }
        });

        nextMissionButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goOneMissionForward();
            }
        });

        table.add(prevMissionButton).left();
        table.add(nextMissionButton).left();

        table.row();

        scoreTable = new ScoreTable(game, skin, roundNumberToShow);
        table.add(scoreTable).fill();

        button("Main Menu", "main menu");
        button("Review", "review");

        Player currentPlayer = playerService.getCurrentPlayer();
        LocalGameState gameState = game.getLocalGameState(currentPlayer.getUsername());
        if (gameState == LocalGameState.YOU_CAN_PLAY || gameState == LocalGameState.WAITING_JUST_FOR_YOU) {
            button("Next mission", "next");
        }

        enableDisableNavButtons();
        pack();
    }

    private void goOneMissionForward() {
        missionNumberToShow++;
        renderMissionScores();
        enableDisableNavButtons();
    }

    private void goOneMissionBack() {
        missionNumberToShow--;
        renderMissionScores();
        enableDisableNavButtons();
    }

    private void enableDisableNavButtons() {
        if (missionNumberToShow <= 1) {
            prevMissionButton.setDisabled(true);
        } else {
            prevMissionButton.setDisabled(false);
        }

        if (missionNumberToShow >= initialMissionNumberToShow) {
            nextMissionButton.setDisabled(true);
        } else {
            nextMissionButton.setDisabled(false);
        }
    }

    private void renderMissionScores() {
        scoreTable.renderMissionScores(missionNumberToShow);
    }

}
