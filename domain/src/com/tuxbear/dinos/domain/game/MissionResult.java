package com.tuxbear.dinos.domain.game;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    public MissionResult() {
    }

    @DynamoDBIgnore
    @JsonIgnore
    public Long getTimeSpent() {
        return moveSequence.moves.get(moveSequence.moves.size()-1).getTimestamp();
    }

    @DynamoDBIgnore
    @JsonIgnore
    public int getNumberOfMoves() {
        return moveSequence.moves.size();
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
