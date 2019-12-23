package com.tuxbear.dinos.ui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tuxbear.dinos.DinosGame;
import com.tuxbear.dinos.domain.user.CognitoTokens;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.services.DataService;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.LocalStorage;
import com.tuxbear.dinos.services.PlayerService;
import com.tuxbear.dinos.services.ResourceContainer;
import com.tuxbear.dinos.services.ServerCallResults;
import com.tuxbear.dinos.services.ServerCallback;

import java.io.IOException;

public class LoginOrRegisterScreen extends AbstractFullScreen {
    PlayerService playerService = IoC.resolve(PlayerService.class);
    DataService dataService = IoC.resolve(DataService.class);
    LocalStorage storage = IoC.resolve(LocalStorage.class);

    TextField usernameTextField = new TextField("", ResourceContainer.skin);
    TextField passwordTextField = new TextField("", ResourceContainer.skin);

    public LoginOrRegisterScreen(Game game) {
        super(game);

        Table ct = rootTable;
        ct.setSkin(ResourceContainer.skin);

        ct.center().top().padTop(150);

        Label header = new Label("Welcome to Dinos", ResourceContainer.skin);

        ct.add(header).height(400).row();

        ct.add("Enter your existing or preferred username and password").pad(50);
        ct.row();

        ct.add("Username:").row();
        ct.add(usernameTextField).width(600).height(120);

        ct.row();

        ct.add("Password:").row();
        ct.add(passwordTextField).width(600).height(120);

        ct.row().height(200);

        TextButton loginButton = new TextButton("Let's go!", ResourceContainer.skin);

        ct.add(loginButton).width(600).height(120).padTop(100);

        loginButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                try {
                    playerService.login(usernameTextField.getText(), passwordTextField.getText(), new ServerCallback<CognitoTokens>() {
                        @Override
                        public void processResult(CognitoTokens result, ServerCallResults status) throws Exception {
                            try {
                                dataService.getPlayerProfile(new ServerCallback<Player>() {
                                    @Override
                                    public void processResult(Player player, ServerCallResults playerFetchStatus) throws Exception {
                                        if (player != null) {
                                            System.out.println("Hello " + player.getUsername());
                                            storage.saveCurrentUser(player);
                                            game.setScreen(new GameListScreen(game));
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                // TODO: Handle comms breakdown?
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }

}
