package com.tuxbear.dinos.ui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.tuxbear.dinos.domain.events.GameEvent;
import com.tuxbear.dinos.domain.events.GameEventListener;
import com.tuxbear.dinos.domain.events.MissionAccomplishedEvent;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.services.DataService;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.LocalStorage;
import com.tuxbear.dinos.services.Logger;
import com.tuxbear.dinos.services.PlayerService;
import com.tuxbear.dinos.services.ResourceContainer;
import com.tuxbear.dinos.services.ServerCallResults;
import com.tuxbear.dinos.services.ServerCallStatus;
import com.tuxbear.dinos.services.ServerCallback;
import com.tuxbear.dinos.services.events.EventBus;
import com.tuxbear.dinos.ui.dialogs.DialogCallback;
import com.tuxbear.dinos.ui.dialogs.LoadingDialog;
import com.tuxbear.dinos.ui.dialogs.MissionEndDialog;
import com.tuxbear.dinos.ui.dialogs.MissionStartDialog;
import com.tuxbear.dinos.ui.widgets.board.BoardWidget;
import com.tuxbear.dinos.ui.widgets.board.DinoActor;
import com.tuxbear.dinos.ui.widgets.board.RoundStatusBar;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class GameScreen extends AbstractFullScreen implements GameEventListener {

    private final Player currentPlayer;
    private BoardWidget boardWidget;
    private RoundStatusBar statusBar;
    private EventBus eventBus = IoC.resolve(EventBus.class);
    private PlayerService playerService = IoC.resolve(PlayerService.class);
    private DataService dataService = IoC.resolve(DataService.class);
    private LocalStorage storage = IoC.resolve(LocalStorage.class);
    private Logger logger = IoC.resolve(Logger.class);

    private MultiplayerGame dinosGameInstance;

    public GameScreen(final Game game, MultiplayerGame dinosGameInstance) throws IOException {
        super(game);
        if (dinosGameInstance == null) {
            logger.log("Trying to start a null-instace game!");
        }
        eventBus.subscribe(this, MissionAccomplishedEvent.class);

        currentPlayer = playerService.getCurrentPlayer();

        this.dinosGameInstance = dinosGameInstance;

        rootTable.setDebug(true);
        rootTable.align(Align.top);

        rootTable.setBackground(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("scene.jpg")))));

        float screenWidth = stage.getViewport().getScreenWidth();
        float tileSize = screenWidth / (dinosGameInstance.getBoard().getColumns() * 1.0f);

        boardWidget = new BoardWidget(dinosGameInstance, tileSize);
        statusBar = new RoundStatusBar(ResourceContainer.skin);

        rootTable.add(statusBar).height(boardWidget.tileSize).top();
        rootTable.row();
        rootTable.add(boardWidget).top();

        Table touchControllers = new Table();
        float touchControllerSize = tileSize * 1.5f;
        touchControllers.add(getTouchControllerForDino(0)).width(touchControllerSize).pad(20);
        touchControllers.add(getTouchControllerForDino(1)).width(touchControllerSize).pad(20);
        touchControllers.add(getTouchControllerForDino(2)).width(touchControllerSize).pad(20);
        touchControllers.add(getTouchControllerForDino(3)).width(touchControllerSize).pad(20);

        rootTable.row();
        rootTable.add(touchControllers).height(touchControllerSize);

        refreshGameUi();
    }

    @NotNull
    private Touchpad getTouchControllerForDino(int dino) {
        Touchpad touchpad = new Touchpad(2f, ResourceContainer.skin);
        touchpad.setResetOnTouchUp(true);

        Texture dinoTextureFromNumber = DinoActor.getDinoTextureFromNumber(dino);
        Sprite dinoKnobSprite = new Sprite(dinoTextureFromNumber);

        dinoKnobSprite.setAlpha(0.8f);
        dinoKnobSprite.setColor(Color.WHITE);
        dinoKnobSprite.setSize(75,75);
        SpriteDrawable drawable = new SpriteDrawable(dinoKnobSprite);

        Sprite backGround = new Sprite(new Texture(Gdx.files.internal("tile.png")));
        touchpad.setStyle(new Touchpad.TouchpadStyle(
            new SpriteDrawable(backGround),
                drawable
        ));

        return touchpad;
    }

    private void refreshGameUi() throws IOException {
        showMissionAccomplishedDialog();
    }

    @Override
    public void dispose() {
        eventBus.dropAllListeners();
        super.dispose();
    }

    private void showMissionStartDialog() {

        MissionStartDialog missionStartDialog = new MissionStartDialog(this.dinosGameInstance, currentPlayer, ResourceContainer.skin);
        missionStartDialog.show(stage, new DialogCallback() {
            @Override
            public void onDialogClose(Object dialogResult) throws IOException {
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
                    public void processResult(MultiplayerGame result, ServerCallResults status) throws Exception {
                        // TODO: handle status FAILED
                        if (status.getStatus().equals(ServerCallStatus.SUCCESS)) {
                            dinosGameInstance = result;
                        } else {
                            // save round result to the local game and to disk for sync later




                        }

                        sendingDialog.hide();
                        showMissionAccomplishedDialog();
                    }
                });
            } catch (IOException e) {
                //TODO: handle breakdown in comms somehow, store to localstorage and sync later?
                throw new RuntimeException(e);
            }
        }
    }

    private void showMissionAccomplishedDialog() throws IOException {
        MissionEndDialog roundEndDialog = new MissionEndDialog(
                this.dinosGameInstance,
                ResourceContainer.skin,
                dinosGameInstance.getCurrentMissionNumber(playerService.getCurrentPlayer().getUsername())
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

                        roundEndDialog.hide();
                        Gdx.graphics.requestRendering();

                        GameScreen.this.showMissionStartDialog();

                        break;
                }
            }
        });
    }
}
