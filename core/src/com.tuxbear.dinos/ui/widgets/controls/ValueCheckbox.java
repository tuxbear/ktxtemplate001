package com.tuxbear.dinos.ui.widgets.controls;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Ole - André Johansen on 06.01.14.
 */
public class ValueCheckbox<T> extends CheckBox {

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    private T value;

    public ValueCheckbox(String text, Skin skin, T value) {
        super(text, skin);
        setValue(value);
    }

    public ValueCheckbox(String text, Skin skin) {
        super(text, skin);
    }

    public ValueCheckbox(String text, CheckBoxStyle style) {
        super(text, style);
    }
}
