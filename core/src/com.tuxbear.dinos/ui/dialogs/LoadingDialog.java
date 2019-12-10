package com.tuxbear.dinos.ui.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 06/01/14 Time: 15:39 To change this template use File | Settings | File
 * Templates.
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(Skin skin, String message) {
        super("", skin);

        this.getContentTable().add(message).center();

    }
    // dinos making smoke signals ?
}
