package com.tuxbear.dinos.ui.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Align;
import com.tuxbear.dinos.*;
import com.tuxbear.dinos.domain.events.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.services.events.*;
import com.tuxbear.dinos.ui.widgets.board.*;
import com.tuxbear.dinos.ui.dialogs.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 03/12/13 Time: 15:07 To change this template use File | Settings | File
 * Templates.
 */
public class GameScreen extends AbstractFullScreen implements GameEventListener {

    private BoardWidget boardWidget;
    private RoundStatusBar statusBar;
    private EventBus eventBus = IoC.resolve(EventBus.class);
    private PlayerService playerService = IoC.resolve(PlayerService.class);
    private DataService dataService = IoC.resolve(DataService.class);
    private Logger logger = IoC.resolve(Logger.class);

    private MultiplayerGame dinosGameInstance;

    public GameScreen(final DinosGame game, MultiplayerGame dinosGameInstance) {
        super(game);
        if (dinosGameInstance == null)
        {
            logger.log("Trying to start a null-instace game!");
        }

        this.dinosGameInstance = dinosGameInstance;

        rootTable.setDebug(true);
        rootTable.align(Align.top);

        rootTable.setBackground(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("scene.jpg")))));

        float screenWidth = stage.getViewport().getScreenWidth();
        float tileSize = screenWidth / (dinosGameInstance.getBoard().getColumns() * 1.0f);

        boardWidget = new BoardWidget(dinosGameInstance, tileSize);
        statusBar = new RoundStatusBar(ResourceContainer.skin);

        Touchpad tp = new Touchpad(2f, ResourceContainer.skin);
        tp.setResetOnTouchUp(true);
        tp.setFillParent(true);

        rootTable.add(statusBar).height(boardWidget.tileSize).top();
        rootTable.row();
        rootTable.add(boardWidget).top();

        eventBus.subscribe(this, MissionAccomplishedEvent.class);

        initGameUI();
    }

    private void initGameUI() {
        Player currentPlayer = playerService.getCurrentPlayer();
        switch (dinosGameInstance.getLocalGameState(currentPlayer.getUsername())) {
            case WAITING_FOR_OPPONENTS:
                showMissionAccomplishedDialog();
                break;
            case WAITING_JUST_FOR_YOU:
            case YOU_CAN_PLAY:
                showMissionAccomplishedDialog();
                break;
            case ENDED:
                showMissionAccomplishedDialog();
                break;
        }
    }

    @Override
    public void dispose() {
        eventBus.dropAllListeners();
        super.dispose();
    }

    private void showMissionStartDialog() {
        MissionStartDialog missionStartDialog = new MissionStartDialog(this.dinosGameInstance, ResourceContainer.skin);
        missionStartDialog.show(stage, new DialogCallback() {
            @Override
            public void onDialogClose(Object dialogResult) {
                String result = dialogResult.toString();
                switch (result) {
                    case "game list":
                        game.setScreen(new GameListScreen(game));
                        break;

                    case "score list":
                        showMissionAccomplishedDialog();
                        break;

                    case "play":
                        // show countdown animation and start the round
                        boardWidget.checkAndStartNextMission();
                        break;
                }

            }
        });
    }

    @Override
    public void processEvent(GameEvent event) {

        if (event instanceof MissionAccomplishedEvent) {
            MissionAccomplishedEvent missionEvent = (MissionAccomplishedEvent) event;
            final LoadingDialog sendingDialog = new LoadingDialog(ResourceContainer.skin, "Sending result...");
            sendingDialog.show(stage);
            try {
                dataService.reportRoundResultsAsync(missionEvent.getResult(), new ServerCallback<MultiplayerGame>() {
                    @Override
                    public void processResult(MultiplayerGame result, ServerCallResults status) {
                        sendingDialog.hide();

                    }
                });
            } catch (IOException e) {
                //TODO: handle breakdown in comms somehow, store to localstorage and sync later?
            }

            showMissionAccomplishedDialog();
        }
    }

    private void showMissionAccomplishedDialog() {
        MissionEndDialog roundEndDialog = new MissionEndDialog(
                this.dinosGameInstance,
                ResourceContainer.skin,
                dinosGameInstance.getCurrentMissionNumber()
        );

        roundEndDialog.show(stage, new DialogCallback() {
            @Override
            public void onDialogClose(Object dialogResult) {
                String result = dialogResult.toString();
                switch (result) {
                    case "main menu":
                        game.setScreen(new GameListScreen(game));
                        break;

                    case "review":

                        break;

                    case "next":
                        showMissionStartDialog();
                        break;
                }

            }
        });
    }
}
