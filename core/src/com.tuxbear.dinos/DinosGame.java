
package com.tuxbear.dinos;

import com.badlogic.gdx.*;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.PlayerService;
import com.tuxbear.dinos.ui.screens.GameListScreen;
import com.tuxbear.dinos.ui.screens.LoginOrRegisterScreen;


public class DinosGame extends Game {

    PlayerService playerService = IoC.resolve(PlayerService.class);

    @Override
    public void create() {
        if (playerService.getCurrentPlayer() == null) {
            setScreen(new LoginOrRegisterScreen(this));
        } else {
            setScreen(new GameListScreen(this));
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
