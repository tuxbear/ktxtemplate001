package com.tuxbear.dinos.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 * Created by Ole - André Johansen on 02.01.14.
 */
public class ResourceContainer {
    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

    public static Skin skin;

    private static float screenDensity = Gdx.graphics.getDensity();

    private static BitmapFont normalFont;
    private static BitmapFont smallFont;
    private static BitmapFont largeFont;

    static {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/Roboto-Condensed.ttf"));
        //FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/stoneage.ttf"));

        FreeTypeFontParameter largeFontSize = getDensitiyIndependentFontParameter(generator, 64);
        FreeTypeFontParameter normalFontSize = getDensitiyIndependentFontParameter(generator, 28);
        FreeTypeFontParameter smallFontSize = getDensitiyIndependentFontParameter(generator, 16);

        smallFont = generator.generateFont(smallFontSize);
        normalFont = generator.generateFont(normalFontSize);
        largeFont = generator.generateFont(largeFontSize);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.get(Label.LabelStyle.class).font = normalFont;
        skin.get(Window.WindowStyle.class).titleFont = largeFont;
        skin.get(TextButton.TextButtonStyle.class).font = normalFont;
    }


    private static FreeTypeFontParameter getDensitiyIndependentFontParameter(FreeTypeFontGenerator generator, int desiredFontSize) {
        FreeTypeFontParameter freeTypeFontParameter = new FreeTypeFontParameter();
        freeTypeFontParameter.size = generator.scaleForPixelHeight(desiredFontSize);
        freeTypeFontParameter.characters = FONT_CHARACTERS;
        return freeTypeFontParameter;
    }
}
