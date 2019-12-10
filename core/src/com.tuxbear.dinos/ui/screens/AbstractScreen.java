package com.tuxbear.dinos.ui.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tuxbear.dinos.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 09/12/13 Time: 15:01 To change this template use File | Settings | File
 * Templates.
 */
public abstract class AbstractScreen implements Screen {

    private Viewport viewPort = new ScalingViewport(Scaling.fit, 1080, 1980);
    Stage stage = new Stage(viewPort);

    final DinosGame game;

    public AbstractScreen(DinosGame game) {
        this.game = game;
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta) {
        Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        stage.act(delta);
        stage.draw();
    }

    /**
     * @see ApplicationListener#resize(int, int)
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }


    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }


    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    public void hide() {
        dispose();
    }


    /**
     * @see ApplicationListener#pause()
     */
    public void pause() {

    }


    /**
     * @see ApplicationListener#resume()
     */
    public void resume() {

    }


    /**
     * Called when this screen should release all resources.
     */
    public void dispose() {
        stage.dispose();
    }
}
