package com.tuxbear.dinos.ui.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.PlayerService;
import com.tuxbear.dinos.services.ResourceContainer;
import com.tuxbear.dinos.ui.widgets.*;

import java.io.IOException;

public class MissionEndDialog extends AbstractCallbackDialog {

    private final ScoreTable scoreTable;

    private final int initialMissionNumberToShow;

    PlayerService playerService = IoC.resolve(PlayerService.class);

    private int missionNumberToShow;

    private final Button prevMissionButton;

    private final Button nextMissionButton;
    private final Label missionNumberLabel;

    public MissionEndDialog(MultiplayerGame game, Skin skin, int roundNumberToShow) throws IOException {
        super("Food found!", skin);
        this.initialMissionNumberToShow = roundNumberToShow;
        this.missionNumberToShow = roundNumberToShow;

        missionNumberLabel = new Label(String.valueOf(roundNumberToShow), new Label.LabelStyle(ResourceContainer.largeFont, Color.FOREST));

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
        table.add(missionNumberLabel).center().expandX();
        table.add(nextMissionButton).right();

        table.row();

        scoreTable = new ScoreTable(game, skin, roundNumberToShow);
        table.add(scoreTable).colspan(3).fill();

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
        missionNumberLabel.setText(String.valueOf(missionNumberToShow));
        renderMissionScores();
        enableDisableNavButtons();
    }

    private void goOneMissionBack() {
        missionNumberToShow--;
        missionNumberLabel.setText(String.valueOf(missionNumberToShow));
        renderMissionScores();
        enableDisableNavButtons();
    }

    private void enableDisableNavButtons() {
        if (missionNumberToShow <= 1) {
            prevMissionButton.setVisible(false);
        } else {
            prevMissionButton.setVisible(true);
        }

        if (missionNumberToShow >= initialMissionNumberToShow) {
            nextMissionButton.setVisible(false);
        } else {
            nextMissionButton.setVisible(true);
        }
    }

    private void renderMissionScores() {
        scoreTable.renderMissionScores(missionNumberToShow);
    }

}
