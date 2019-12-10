package com.tuxbear.dinos.ui.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.tuxbear.dinos.services.ResourceContainer;

/**
 * User: tuxbear Date: 28/01/14 Time: 14:17
 */
public class LoginDialog extends AbstractCallbackDialog {
    public LoginDialog(String title, Skin skin) {
        super(title, skin);


        Table ct = getContentTable();

        ct.add("Name:");
        ct.add(new TextField("", ResourceContainer.skin));

        ct.row();

        ct.add("Password:");
        ct.add(new TextField("", ResourceContainer.skin));


        getButtonTable().add(new TextButton("<<", ResourceContainer.skin));
        getButtonTable().add(new TextButton("Register", ResourceContainer.skin));
    }
}
