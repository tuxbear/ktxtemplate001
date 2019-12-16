package com.tuxbear.dinos.domain.game;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class BoardPosition {

    public static BoardPosition OFF_BOARD = new BoardPosition(-1000, -1000);
    @DynamoDBAttribute
    public int x;
    @DynamoDBAttribute
    public int y;

    public BoardPosition() {
    }

    public BoardPosition(int x, int y) {
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
                return new BoardPosition(x, y - 1);
            case Down:
                return new BoardPosition(x, y + 1);
            case Left:
                return new BoardPosition(x - 1, y);
            case Right:
                return new BoardPosition(x + 1, y);
            default:
                throw new IllegalArgumentException("inDirection");
        }
    }

    public int getDistanceTo(BoardPosition position) {
        return Math.abs(x - position.x + y - position.y);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BoardPosition && equals((BoardPosition) other);
    }

    public boolean equals(BoardPosition other) {
        return x == other.x && y == other.y;
    }

    public String toString() {
        return String.format("(%s,%s)", x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
