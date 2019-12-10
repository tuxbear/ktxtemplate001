package com.tuxbear.dinos.domain.game;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 08/12/13 Time: 12:56 To change this template use File | Settings | File
 * Templates.
 */
public class Move {

    public int getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(int pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    int pieceNumber;
    Direction direction;

}
