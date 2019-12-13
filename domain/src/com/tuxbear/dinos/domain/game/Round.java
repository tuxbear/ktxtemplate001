package com.tuxbear.dinos.domain.game;

import com.tuxbear.dinos.domain.user.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Round {
    private String id;

    private int numberInGame;

    private Mission mission;

    private List<MissionResult> results = new ArrayList<>();

    public int getNumberInGame() {
        return numberInGame;
    }

    public void setNumberInGame(int numberInGame) {
        this.numberInGame = numberInGame;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public List<MissionResult> getResults() {
        return results;
    }

    public void setResults(List<MissionResult> results) {
        this.results = results;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addResult(MissionResult result) {
        MissionResult existingResult = getResultForUsername(result.getPlayerId());

    }

    public RoundState getRoundState(MultiplayerGame game) {
        RoundState state = new RoundState();
        Map<String, MissionResult> missionResults = new HashMap<>();
        for (MissionResult result : results) {
            missionResults.put(result.getPlayerId(), result);
        }

        state.setNumberOfPlayersFinished(results.size());
        state.setNumberOfPlayersUnfinished(game.getPlayers().size() - results.size());

        return state;
    }

    public MissionResult getResultForPlayer(Player user) {
        return getResultForUsername(user.getId());
    }

    public MissionResult getResultForUsername(String username) {
        for (MissionResult result : results) {
            if (result.getPlayerId().equals(username)) {
                return result;
            }
        }

        return null;
    }
}
