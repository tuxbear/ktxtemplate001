package com.tuxbear.dinos.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.tuxbear.dinos.services.ResourceContainer;

public class HeaderLabel extends Label {
    public HeaderLabel(CharSequence text) {
        super(text, ResourceContainer.skin);

    }
}
