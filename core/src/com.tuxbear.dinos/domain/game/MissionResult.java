package com.tuxbear.dinos.domain.game;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 03/01/14 Time: 17:18 To change this template use File | Settings | File
 * Templates.
 */
public class MissionResult {

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

    private String gameId;
    private String playerId;
    private String missionId;
    private MoveSequence moveSequence;
    private int score;
    private int millisecondsSpent;
    private int numberOfMoves;


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
