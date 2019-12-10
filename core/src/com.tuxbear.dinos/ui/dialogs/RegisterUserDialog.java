package com.tuxbear.dinos.ui.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.tuxbear.dinos.services.*;

/**
 * User: tuxbear Date: 28/01/14 Time: 14:17
 */
public class RegisterUserDialog extends AbstractCallbackDialog {
    public RegisterUserDialog(String title, Skin skin) {
        super(title, skin);


        Table ct = getContentTable();

        ct.add("Username:");
        ct.add(new TextField("", ResourceContainer.skin));

        ct.row();

        ct.add("Password:");
        ct.add(new TextField("", ResourceContainer.skin));


        getButtonTable().add(new TextButton("<<", ResourceContainer.skin));
        getButtonTable().add(new TextButton("Register", ResourceContainer.skin));
    }
}
