package com.tuxbear.dinos.domain.game;

import java.util.*;

/**
 * Created by Ole - André Johansen on 02.01.14.
 */
public class Board {

    private String name;
    private int rows;
    private int columns;

    private List<Wall> walls = new ArrayList<>();

    public void addWall(int fromX, int fromY, int toX, int toY){
        walls.add(new Wall( fromX, fromY, toX, toY));
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }


    public boolean isInsideBoard(int targetX, int targetY) {
        return targetX >= 0 && targetX < columns && targetY >= 0 && targetY < rows;
    }


    private boolean canMove(int fromX, int fromY, Direction inDirection, List<BoardPosition> occupiedPositions) {
        int targetX = fromX,
                targetY = fromY;
        switch (inDirection) {
            case Up:
                targetY -= 1;
                break;
            case Down:
                targetY += 1;
                break;
            case Left:
                targetX -= 1;
                break;
            case Right:
                targetX += 1;
                break;
            default:
                throw new IllegalArgumentException("inDirection");
        }

        return
                isInsideBoard(targetX, targetY) && // there is a BoardSquare in this direction
                        isInsideBoard(fromX, fromY) &&
                        !wallExistsBetween(fromX, fromY, targetX, targetY) &&
                        !hasDino(targetX, targetY, occupiedPositions);
    }

    public BoardPosition getPositionInDirection(BoardPosition fromPosition, Direction direction, List<BoardPosition> occupiedPositions) {

        int targetX = fromPosition.getX(), targetY = fromPosition.getY();
        switch (direction) {
            case Up:
                while (canMove(targetX, targetY, direction, occupiedPositions)) {
                    targetY -= 1;
                }
                break;
            case Down:
                while (canMove(targetX, targetY, direction, occupiedPositions)) {
                    targetY += 1;
                }
                break;
            case Left:
                while (canMove(targetX, targetY, direction, occupiedPositions)) {
                    targetX -= 1;
                }
                break;
            case Right:
                while (canMove(targetX, targetY, direction, occupiedPositions)) {
                    targetX += 1;
                }
                break;
            default:
                throw new IllegalArgumentException("inDirection");
        }

        return new BoardPosition(targetX, targetY);
    }


    private boolean wallExistsBetween(int x1, int y1, int x2, int y2) {
        for(Wall w : walls) {
            if  ((w.getFrom().x == x1 && w.getFrom().y == y1 && w.getTo().x == x2 && w.getTo().y == y2) ||
                    (w.getFrom().x == x2 && w.getFrom().y == y2 && w.getTo().x == x1 && w.getTo().y == y1)){
                return true;
            }
        }

        return false;
    }



    private boolean hasDino(int x, int y, List<BoardPosition> dinoPositions) {
        BoardPosition targetPosition = new BoardPosition(x,y);
        return dinoPositions.contains(targetPosition);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static Board getByName(String name){
        int rows = 10;
        int columns = 8;

        Board board = new Board();
        board.setColumns(columns);
        board.setRows(rows);

        board.addWall(0,8, 0,7);
        board.addWall(2,9, 3,9);
        board.addWall(5,9, 6,9);
        board.addWall(3,8, 3,7);
        board.addWall(2,7, 3,7);
        board.addWall(7,7, 7,6);
        board.addWall(5,6, 6,6);
        board.addWall(5,6, 5,5);
        board.addWall(1,5, 1,4);
        board.addWall(1,4, 2,4);
        board.addWall(3,3, 4,3);
        board.addWall(4,4, 4,3);
        board.addWall(0,3, 0,2);
        board.addWall(7,3, 7,2);
        board.addWall(1,0, 2,0);
        board.addWall(4,0, 5,0);

        return board;

    }

}
