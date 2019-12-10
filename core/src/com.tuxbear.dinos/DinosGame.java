
package com.tuxbear.dinos;

import com.badlogic.gdx.*;
import com.tuxbear.dinos.ui.screens.GameListScreen;


public class DinosGame extends Game {
    @Override
    public void create() {
        setScreen(new GameListScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

}
