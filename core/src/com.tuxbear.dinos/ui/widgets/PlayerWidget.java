package com.tuxbear.dinos.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.tuxbear.dinos.domain.user.*;

public class PlayerWidget extends Table {
    public PlayerWidget(String username, Skin skin) {
        super(skin);
        add(username);
    }
}
