package com.tuxbear.dinos.ui.widgets.board;

import com.badlogic.gdx.scenes.scene2d.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.Logger;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 06/12/13 Time: 17:22 To change this template use File | Settings | File
 * Templates.
 */
public abstract class BoardElement extends Actor {
    private final BoardWidget boardWidget;
    Logger log = IoC.resolve(Logger.class);

    private BoardPosition position;

    public BoardElement (BoardWidget boardWidget, BoardPosition position) {

        this.boardWidget = boardWidget;
        this.position = position;
    }

    public float getDrawX() {
        return boardWidget.tileSize * position.x;
    }

    public float getDrawY() {
        return boardWidget.getPrefHeight() - (boardWidget.tileSize * (position.y + 1));
    }

    public BoardPosition getBoardPosition() {
        return position;
    }

    public void setBoardPosition(BoardPosition newPosition) {
        position = newPosition;
    }

    public void updatePositionFromBoardPosition() {
        setX(getDrawX());
        setY(getDrawY());
    }

    public float getTileSize() {
        return boardWidget.tileSize;
    }
}
