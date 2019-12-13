package com.tuxbear.dinos.domain.game;

public class BoardPosition {
    public int x;
    public int y;

    public static BoardPosition OFF_BOARD = new BoardPosition(-1000,-1000);

    public BoardPosition() {
    }

    public BoardPosition(int x, int y) {
        //To change body of created methods use File | Settings | File Templates.
        this.x = x;
        this.y = y;
    }

    public boolean isAdjacent(BoardPosition position, Direction direction) {
        BoardPosition expectedPosition = getPositionInDirection(direction);
        return position.x == expectedPosition.x && position.y == expectedPosition.y;
    }

    public BoardPosition getPositionInDirection(Direction direction) {
        switch (direction) {
            case Up:
                    return new BoardPosition(x,y-1);
            case Down:
                return new BoardPosition(x,y+1);
            case Left:
                return new BoardPosition(x-1,y);
            case Right:
                return new BoardPosition(x+1,y);
            default:
                throw new IllegalArgumentException("inDirection");
        }
    }

    public int getDistanceTo(BoardPosition position){
        return Math.abs(x-position.x + y - position.y);
    }

    @Override
    public boolean equals(Object other)
    {
        return other instanceof BoardPosition && equals((BoardPosition)other);
    }

    public boolean equals(BoardPosition other) {
        return x == other.x && y == other.y;
    }

    public String toString() {
        return String.format("(%s,%s)", x,y);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
