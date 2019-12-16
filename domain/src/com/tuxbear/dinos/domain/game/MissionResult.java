package com.tuxbear.dinos.domain.game;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class MissionResult {

    @DynamoDBAttribute
    private String gameId;

    @DynamoDBAttribute
    private String playerId;

    @DynamoDBAttribute
    private String missionId;

    @DynamoDBAttribute
    private MoveSequence moveSequence;

    @DynamoDBAttribute
    private int score;

    @DynamoDBAttribute
    private int millisecondsSpent;

    @DynamoDBAttribute
    private int numberOfMoves;

    public MissionResult() {
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public MoveSequence getMoveSequence() {
        return moveSequence;
    }

    public void setMoveSequence(MoveSequence moveSequence) {
        this.moveSequence = moveSequence;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMillisecondsSpent() {
        return millisecondsSpent;
    }

    public void setMillisecondsSpent(int millisecondsSpent) {
        this.millisecondsSpent = millisecondsSpent;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
