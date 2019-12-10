package com.tuxbear.dinos.ui.widgets.board;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.tuxbear.dinos.domain.game.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 08/12/13 Time: 13:02 To change this template use File | Settings | File
 * Templates.
 */
public class MissionActor extends BoardElement{

    private Texture texture;

    public MissionActor(BoardWidget boardWidget, BoardPosition position) {
        super(boardWidget, position);
        texture = new Texture(Gdx.files.internal("nest.png"));

        AlphaAction alphaAction = Actions.alpha(0.6f, 1.0f, Interpolation.fade);
        addAction(Actions.forever(alphaAction));
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }


    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture, getDrawX(), getDrawY(), getTileSize(), getTileSize());
    }
}
