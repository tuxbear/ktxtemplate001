package com.tuxbear.dinos.domain.game;

import com.tuxbear.dinos.domain.events.GameEvent;
import com.tuxbear.dinos.domain.events.GameOverEvent;
import com.tuxbear.dinos.domain.events.RoundEndEvent;
import com.tuxbear.dinos.domain.user.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class MultiplayerGame {

    private String id;
    private String owner;
    private String state;
    private Integer version;
    private int currentMissionNumber = 1;
    private ArrayList<Mission> missions = new ArrayList<>();
    private ArrayList<MissionResult> missionResults = new ArrayList<>();
    private String difficulty;
    private Board board;
    private HashMap<Integer, BoardPosition> initialPiecePositions = new HashMap<>();
    private ArrayList<Player> players = new ArrayList<>();

    private Date lastUpdateFromServer;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GlobalGameState getGlobalGameState() {
        return currentMissionNumber > getNumberOfMissions() ? GlobalGameState.ENDED : GlobalGameState.ACTIVE;
    }

    public LocalGameState getLocalGameState(String username) {
        GlobalGameState globalState = getGlobalGameState();
        if (globalState == GlobalGameState.ENDED || globalState == GlobalGameState.ABORTED) {
            return LocalGameState.ENDED;
        }

        MissionResult myResults = getMissionResultForPlayer(username, getCurrentMission().getId());

        if (myResults != null) {
            return LocalGameState.WAITING_FOR_OPPONENTS;
        } else {
            return LocalGameState.YOU_CAN_PLAY;
        }
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Mission getMission(int missionNumber) {
        return missions.get(missionNumber - 1);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Map<Integer, BoardPosition> getInitialPiecePositions() {
        return initialPiecePositions;
    }

    public void setInitialPiecePositions(HashMap<Integer, BoardPosition> initialPiecePositions) {
        this.initialPiecePositions = initialPiecePositions;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Mission getCurrentMission() {
        return missions.get(getCurrentMissionNumber() - 1);
    }

    /**
     * applies a mission result to the game and returns the resulting Game Events
     * @param result
     * @return
     */
    public List<GameEvent> reportResultForCurrentRound(MissionResult result) {

        MissionResult existingResult = getMissionResultForPlayer(result.getPlayerId(), getCurrentMission().getId());
        if (existingResult != null) {
            throw new IllegalArgumentException("Cannot add result, it already exists for this the user " + result.getPlayerId());
        }

        missionResults.add(result);
        List<GameEvent> events = new ArrayList<>();
        if (isMissionCompletedByAllPlayers(getCurrentMission().getId())) {
            // round end
            events.add(new RoundEndEvent());

            if (getCurrentMissionNumber() == getNumberOfMissions()) {
                events.add(new GameOverEvent());
            }
        }

        return events;
    }

    public long getTotalScoreForPlayer(Player player) {
        long score = 0;
        for (MissionResult result : missionResults) {
            if (result != null && result.getPlayerId().equals(player.getUsername())) {
                score += result.getScore();
            }
        }

        return score;
    }

    public String getOpponentString(String currentPlayerId) {

        List<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            if (!player.getUsername().equals(currentPlayerId)) {
                playerNames.add(player.getUsername());
            }
        }


        if (playerNames.size() > 1) {
            return playerNames.get(0) + ", " + playerNames.get(1) + " + " + (playerNames.size() - 1);
        } else {
            return playerNames.size() == 0 ? "Solo game?" : playerNames.get(0);
        }
    }


    public boolean isMissionCompletedByAllPlayers(String missionId) {

        for (Player player : getPlayers()) {
            MissionResult result = getMissionResultForPlayer(player.getUsername(), missionId);
            if (result == null) {
                return false;
            }
        }
        return true;
    }

    public MissionResult getMissionResultForPlayer(String player, String missionId) {
        for (MissionResult result : missionResults) {
            if (result.getPlayerId().equals(player) && missionId.equals(result.getMissionId())) {
                return result;
            }
        }

        return null;
    }

    public int getNumberOfMissions() {
        return missions.size();
    }

    public int getCurrentMissionNumber() {
        return currentMissionNumber;
    }

    public void setCurrentMissionNumber(int currentMissionNumber) {
        this.currentMissionNumber = currentMissionNumber;
    }

    public ArrayList<Mission> getMissions() {
        return missions;
    }

    public void setMissions(ArrayList<Mission> missions) {
        this.missions = missions;
    }

    public ArrayList<MissionResult> getMissionResults() {
        return missionResults;
    }

    public void setMissionResults(ArrayList<MissionResult> missionResults) {
        this.missionResults = missionResults;
    }

    public int getPlayerRank(String playerId) {
        HashMap<String, Long> playerScores = new HashMap<>();
        TreeSet sortedScores = new TreeSet();
        for (Player player : players) {
            long score = getTotalScoreForPlayer(player);
            playerScores.put(player.getUsername(), score);
            sortedScores.add(score);

        }

        Long playerScore = playerScores.get(playerId);

        return new ArrayList(sortedScores).indexOf(playerScore) + 1;
    }

    public Date getLastUpdateFromServer() {
        return lastUpdateFromServer;
    }

    public void setLastUpdateFromServer(Date lastUpdateFromServer) {
        this.lastUpdateFromServer = lastUpdateFromServer;
    }
}
