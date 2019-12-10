package com.tuxbear.dinos.ui.widgets.board;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.*;
import com.tuxbear.dinos.domain.game.BoardPosition;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 02/12/13 Time: 18:27 To change this template use File | Settings | File
 * Templates.
 */
public class Tile extends BoardElement implements Disposable {

    private final Texture tileTexture;
    private final Sprite tileSprite;

    private float size;

    private BoardWidget boardWidget;

    private boolean isTouched;

    public Tile (int x, int y, float size, BoardWidget boardWidget) {
        super(boardWidget, new BoardPosition(x,y));
        this.size = size;
        this.boardWidget = boardWidget;
        tileTexture = new Texture(Gdx.files.internal("tile_dark.png"));
        tileSprite = new Sprite(tileTexture);
        tileSprite.setSize(size,size);

        this.setSize(size, size);
        // set bounds
        this.setBounds(getDrawX(), getDrawY(), size, size);
        this.setVisible(true);
        this.setTouchable(Touchable.enabled);
        registerEventHandlers();
        updatePositionFromBoardPosition();
    }

    @Override
    public void draw(Batch batch, float alpha ){
        updatePositionFromBoardPosition();
        if (isTouched) {
            batch.draw(tileTexture, getX(), getY(), size*1.1f, size*1.1f);
        } else {
            batch.draw(tileTexture, getX(), getY(), size, size);
        }
    }

    @Override
    public void dispose() {
        tileTexture.dispose();
    }


    private void registerEventHandlers() {
        this.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = true;
                log.log(getBoardPosition().toString());
                log.log(String.format("(%s,%s)", getX(), getY()));
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = false;
            }
        });
    }
}
