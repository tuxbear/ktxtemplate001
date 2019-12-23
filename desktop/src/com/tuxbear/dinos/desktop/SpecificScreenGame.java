
package com.tuxbear.dinos.desktop;

import com.badlogic.gdx.Game;
import com.tuxbear.dinos.domain.game.GameGenerator;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.PlayerService;
import com.tuxbear.dinos.ui.screens.GameScreen;

import java.io.IOException;


public class SpecificScreenGame extends Game {

    private PlayerService playerService = IoC.resolve(PlayerService.class);

    @Override
    public void create() {
        try {
            setScreen(new GameScreen(this, GameGenerator.INSTANCE.initGame(10, 10)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
