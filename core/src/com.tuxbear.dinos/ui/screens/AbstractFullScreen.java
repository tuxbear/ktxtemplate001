package com.tuxbear.dinos.ui.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tuxbear.dinos.DinosGame;

/**
 * Created by Ole - André Johansen on 02.01.14.
 */
public abstract class AbstractFullScreen extends AbstractScreen {
    Table rootTable;

    public AbstractFullScreen(DinosGame game) {
        super(game);

        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        rootTable.invalidateHierarchy();
    }
}
