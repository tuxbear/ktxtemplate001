package com.tuxbear.dinos.domain.game;

import java.util.*;

public class MoveSequence {

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    List<Move> moves;

    public MoveSequence() {
        this.moves = new ArrayList<>();
    }
}
