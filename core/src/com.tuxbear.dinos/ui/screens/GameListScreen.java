package com.tuxbear.dinos.ui.screens;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.tuxbear.dinos.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.ui.dialogs.*;
import com.tuxbear.dinos.ui.widgets.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by Ole - André Johansen on 05.01.14.
 */
public class GameListScreen extends AbstractFullScreen {

    private DataService dataService = IoC.resolve(DataService.class);
    private PlayerService playerService = IoC.resolve(PlayerService.class);

    private final GameListWidget gameListWidget;

    private ArrayList<MultiplayerGame> dinoGames;

    private final LoadingDialog loadingGamesProgressDialog;

    public GameListScreen(final DinosGame game) {
        super(game);

        final Skin skin = ResourceContainer.skin;
        loadingGamesProgressDialog = new LoadingDialog(ResourceContainer.skin, "Loading games...");

        TextButton newGameButton = new TextButton("New Game", skin);
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CreateGameDialog createGameDialog = null;

                try {
                    createGameDialog = new CreateGameDialog(skin);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                createGameDialog.show(stage, new DialogCallback() {
                    @Override
                    public void onDialogClose(Object dialogResult) throws IOException {
                        dinoGames.add((MultiplayerGame) dialogResult);
                        gameListWidget.setGameList(dinoGames);
                    }
                });
            }
        });

        rootTable.add(newGameButton);

        TextButton refreshButton = new TextButton("Refresh", skin);
        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                refreshGameList();
            }
        });

        rootTable.add(refreshButton).row();
        gameListWidget = new GameListWidget(ResourceContainer.skin, new GameListWidget.GameSelectedListener() {
            @Override
            public void onGameSelected(MultiplayerGame dinoGame) throws IOException {
                game.setScreen(new GameScreen(game, dinoGame));
            }
        });

        ScrollPane gameListPane = new ScrollPane(gameListWidget, ResourceContainer.skin);

        rootTable.add(gameListPane).colspan(2).fill();
        rootTable.setFillParent(true);
        refreshGameList();
    }

    private void refreshGameList() {
        loadingGamesProgressDialog.show(stage);

        try {
            dataService.getActiveGamesAsync(new ServerCallback<MultiplayerGame[]>() {
                @Override
                public void processResult(MultiplayerGame[] result, ServerCallResults status) throws Exception {

                    if (status.getStatus().equals(ServerCallStatus.LOGIN_REQUIRED)) {
                        game.setScreen(new LoginOrRegisterScreen(game));
                    } else if (status.getStatus().equals(ServerCallStatus.FAILURE)) {
                        // TODO SHOW SOME ERRROR
                    } else {
                        dinoGames = new ArrayList<>(Arrays.asList(result));
                        gameListWidget.setGameList(dinoGames);
                        rootTable.invalidateHierarchy();
                    }

                    loadingGamesProgressDialog.hide();
                }
            });
        } catch (IOException e) {
            // TODO: error notification and handling
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
