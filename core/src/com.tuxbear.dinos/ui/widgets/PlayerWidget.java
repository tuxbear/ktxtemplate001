package com.tuxbear.dinos.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.tuxbear.dinos.domain.user.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 03/01/14 Time: 17:53 To change this template use File | Settings | File
 * Templates.
 */
public class PlayerWidget extends Table {
    public PlayerWidget(Player player, Skin skin) {
        super(skin);
        add(player.getUsername());
    }
}
