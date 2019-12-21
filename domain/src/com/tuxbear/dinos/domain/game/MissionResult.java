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
    private int firstMoveScore;

    @DynamoDBAttribute
    private int moveScore;

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

    public int getFirstMoveScore() {
        return firstMoveScore;
    }

    public void setFirstMoveScore(int firstMoveScore) {
        this.firstMoveScore = firstMoveScore;
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

    public int getMoveScore() {
        return moveScore;
    }

    public void setMoveScore(int moveScore) {
        this.moveScore = moveScore;
    }
}
