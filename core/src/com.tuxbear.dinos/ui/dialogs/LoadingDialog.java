package com.tuxbear.dinos.ui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.tuxbear.dinos.services.ResourceContainer;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 06/01/14 Time: 15:39 To change this template use File | Settings | File
 * Templates.
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(Skin skin, String message) {
        super("", skin);

        Label messageLabel = new Label(message, new Label.LabelStyle(ResourceContainer.largeFont, Color.FIREBRICK));

        getContentTable().setBackground(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui/loading.jpg")))));

        setWidth(Gdx.graphics.getWidth());
        setHeight(Gdx.graphics.getHeight());

        this.getContentTable().add(messageLabel).center();

    }
    // dinos making smoke signals ?
}
