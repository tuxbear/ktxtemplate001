package com.tuxbear.dinos.ui.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tuxbear.dinos.services.ResourceContainer;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 03/01/14 Time: 16:24 To change this template use File | Settings | File
 * Templates.
 */
public abstract class AbstractCallbackDialog extends Dialog {
    private DialogCallback callbackHandler;

    public AbstractCallbackDialog(String title, Skin skin) {
        super(title, skin);

        getTitleLabel().setStyle(new Label.LabelStyle(ResourceContainer.normalFont, Color.BLACK));

        setMovable(false);
        invalidateHierarchy();
    }

    public void show(Stage stage, DialogCallback callbackHandler) {
        super.show(stage);
        this.callbackHandler = callbackHandler;
    }

    @Override
    public void result(Object result) {
        if(callbackHandler != null) {
            try {
                callbackHandler.onDialogClose(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
