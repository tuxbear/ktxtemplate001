package com.tuxbear.dinos.domain.game;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.util.ArrayList;
import java.util.List;

@DynamoDBDocument
public class MoveSequence {

    @DynamoDBAttribute
    List<Move> moves;

    public MoveSequence(List<Move> moves) {
        this.moves = moves;
    }

    public MoveSequence() {
        this.moves = new ArrayList<>();
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
}
