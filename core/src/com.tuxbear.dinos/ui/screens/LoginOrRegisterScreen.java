package com.tuxbear.dinos.ui.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tuxbear.dinos.DinosGame;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.PlayerService;
import com.tuxbear.dinos.services.ResourceContainer;

public class LoginOrRegisterScreen extends AbstractFullScreen {

    PlayerService playerService = IoC.resolve(PlayerService.class);


    TextField usernameTextField = new TextField("", ResourceContainer.skin);
    TextField passwordTextField = new TextField("", ResourceContainer.skin);


    public LoginOrRegisterScreen(DinosGame game) {
        super(game);

        Table ct = rootTable;
        ct.setSkin(ResourceContainer.skin);

        ct.center();

        ct.add("Enter your existing or preferred username and password");
        ct.row();

        ct.add("Username:");
        ct.add(usernameTextField);

        ct.row();

        ct.add("Password:");
        ct.add(passwordTextField);

        ct.row();

        TextButton backButton = new TextButton("<<", ResourceContainer.skin);

        ct.add(backButton);

        TextButton loginButton = new TextButton("Let's go!", ResourceContainer.skin);

        ct.add(loginButton);

        loginButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                playerService.login(usernameTextField.getText(), passwordTextField.getText());

                game.setScreen(new GameListScreen(game));
            }
        });

    }

}
