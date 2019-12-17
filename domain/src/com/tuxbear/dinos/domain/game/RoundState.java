package com.tuxbear.dinos.domain.game;

import java.util.Date;
import java.util.Map;

public class RoundState {

    private Date expires; // implement?
    private Map<String, MissionResult> resultsPerPlayer;
    private int numberOfPlayersFinished;
    private int numberOfPlayersUnfinished;

    public Map<String, MissionResult> getResultsPerPlayer() {
        return resultsPerPlayer;
    }

    public void setResultsPerPlayer(Map<String, MissionResult> resultsPerPlayer) {
        this.resultsPerPlayer = resultsPerPlayer;
    }

    public int getNumberOfPlayersFinished() {
        return numberOfPlayersFinished;
    }

    public void setNumberOfPlayersFinished(int numberOfPlayersFinished) {
        this.numberOfPlayersFinished = numberOfPlayersFinished;
    }

    public int getNumberOfPlayersUnfinished() {
        return numberOfPlayersUnfinished;
    }

    public void setNumberOfPlayersUnfinished(int numberOfPlayersUnfinished) {
        this.numberOfPlayersUnfinished = numberOfPlayersUnfinished;
    }

    public boolean isRoundOver() {
        return numberOfPlayersUnfinished == 0;
    }
}
