package com.tuxbear.dinos.ui.widgets.board;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.tuxbear.dinos.*;
import com.tuxbear.dinos.domain.game.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 06/12/13 Time: 19:01 To change this template use File | Settings | File
 * Templates.
 */
public class WallActor extends BoardElement {

    private final Texture wallTexture;

    private  float wallWidth;

    private  float wallHeight;

    BoardPosition fromTile;

    BoardPosition toTile;

    public WallActor(Wall wall, BoardWidget boardWidget, float width, float tileSize) {
        super(boardWidget, wall.getFrom());
        this.wallWidth = tileSize;
        this.wallHeight = width;

        fromTile = wall.getFrom();
        toTile = wall.getTo();

        if (fromTile.isAdjacent(toTile, Direction.Up) || fromTile.isAdjacent(toTile, Direction.Right)) {
            setBoardPosition(toTile);
        }

        wallTexture = new Texture(Gdx.files.internal(Constants.wallTexturePath));
        doWallRotation();
        updatePositionFromBoardPosition();
        setBounds(getDrawX(), getDrawY(), width, tileSize);
    }

    private void doWallRotation() {
        if (fromTile.x != toTile.x) {
            float tmp = wallHeight;
            wallHeight = wallWidth;
            wallWidth = tmp;
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(wallTexture, getDrawX(), getDrawY(), wallWidth, wallHeight);
    }
}
