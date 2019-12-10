package com.tuxbear.dinos.domain.game;

/**
 * Created by Ole - André Johansen on 02.01.14.
 */
public class Wall {

    private BoardPosition from;
    private BoardPosition to;

    public Wall() {
    }

    public Wall(int fromX, int fromY, int toX, int toY){
        from = new BoardPosition(fromX, fromY);
        to = new BoardPosition(toX, toY);
    }

    public BoardPosition getTo() {
        return to;
    }

    public void setTo(BoardPosition to) {
        this.to = to;
    }

    public BoardPosition getFrom() {
        return from;
    }

    public void setFrom(BoardPosition from) {
        this.from = from;
    }
}
