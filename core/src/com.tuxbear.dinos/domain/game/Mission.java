package com.tuxbear.dinos.domain.game;

/**
 * Created by Ole - André Johansen on 02.01.14.
 */
public class Mission {

    public Mission() {
    }

    private String id;

    private int shortestPath;

    public int getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(int pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    int pieceNumber;

    public BoardPosition getPosition() {
        return position;
    }

    public void setPosition(BoardPosition position) {
        this.position = position;
    }

    BoardPosition position;

    private MoveSequence solution;

    public MoveSequence getSolution() {
        return solution;
    }

    public void setSolution(MoveSequence solution) {
        this.solution = solution;
    }

    public int getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(int shortestPath) {
        this.shortestPath = shortestPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
