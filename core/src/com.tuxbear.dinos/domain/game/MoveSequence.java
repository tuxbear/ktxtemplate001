package com.tuxbear.dinos.domain.game;

import java.util.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 08/12/13 Time: 12:57 To change this template use File | Settings | File
 * Templates.
 */
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
