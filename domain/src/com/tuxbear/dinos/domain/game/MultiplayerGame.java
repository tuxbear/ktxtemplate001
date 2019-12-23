package com.tuxbear.dinos.domain.game;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuxbear.dinos.domain.events.GameEvent;
import com.tuxbear.dinos.domain.events.GameOverEvent;
import com.tuxbear.dinos.domain.events.RoundEndEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "gamesTable")
public class MultiplayerGame {

    @DynamoDBHashKey
    private String id;

    @DynamoDBAttribute
    private Date created;

    @DynamoDBAttribute
    private String owner;

    @DynamoDBTypeConvertedEnum
    private GlobalGameState state;

    @DynamoDBVersionAttribute
    private Integer version;

    @DynamoDBAttribute
    private List<Mission> missions = new ArrayList<>();

    @DynamoDBAttribute
    private List<MissionResult> missionResults = new ArrayList<>();

    @DynamoDBAttribute
    private String difficulty;

    @DynamoDBAttribute
    private Board board;

    @DynamoDBAttribute
    private Map<String, BoardPosition> initialPiecePositions = new HashMap<>();

    @DynamoDBAttribute
    private Set<String> players = new HashSet<>();

    @DynamoDBAttribute
    private Date lastUpdated;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public GlobalGameState getState() {
        return state;
    }

    public void setState(GlobalGameState state) {
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

    @DynamoDBIgnore
    public GlobalGameState calculateGlobalGameState() {
        return missionResults.size() == missions.size() * players.size() ? GlobalGameState.ENDED : GlobalGameState.ACTIVE;
    }

    @DynamoDBIgnore
    public LocalGameState getLocalGameState(String username) {
        GlobalGameState globalState = calculateGlobalGameState();
        if (globalState == GlobalGameState.ENDED || globalState == GlobalGameState.ABORTED) {
            return LocalGameState.ENDED;
        }

        boolean haveCompletedAllMissions = isAllMissionsCompletedByPlayer(username);

        if (haveCompletedAllMissions) {
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

    public Map<String, BoardPosition> getInitialPiecePositions() {
        return initialPiecePositions;
    }

    public void setInitialPiecePositions(Map<String, BoardPosition> initialPiecePositions) {
        this.initialPiecePositions = initialPiecePositions;
    }

    public Set<String> getPlayers() {
        return players;
    }

    public void setPlayers(Set<String> players) {
        this.players = players;
    }

    @JsonIgnore
    @DynamoDBIgnore
    public Mission getCurrentMission(String playerId) {
        return missions.get(getCurrentMissionNumber(playerId) - 1);
    }

    /**
     * applies a mission result to the game and returns the resulting Game Events
     *
     * @param result
     * @return
     */
    public List<GameEvent> reportResultForCurrentRound(MissionResult result) {
        MissionResult existingResult = getMissionResultForPlayer(result.getPlayerId(), getCurrentMission(result.getPlayerId()).getId());
        if (existingResult != null) {
            throw new IllegalArgumentException("Cannot add result, it already exists for this the user " + result.getPlayerId());
        }

        missionResults.add(result);

        List<GameEvent> events = new ArrayList<>();
        if (calculateGlobalGameState() == GlobalGameState.ENDED) {
            events.add(new GameOverEvent());
        }

        return events;
    }

    public long getTotalMovesForPlayer(String player) {
        long moves = 0;
        for (MissionResult result : missionResults) {
            if (result != null && result.getPlayerId().equals(player)) {
                moves += result.getNumberOfMoves();
            }
        }

        return moves;
    }

    public long getTotalTimeSpent(String player) {
        long timeSpent = 0;
        for (MissionResult result : missionResults) {
            if (result != null && result.getPlayerId().equals(player)) {
                timeSpent += result.getTimeSpent();
            }
        }

        return timeSpent;
    }

    public String getOpponentString(String currentPlayerId) {
        List<String> playerNames = new ArrayList<>();
        for (String player : players) {
            if (!player.equals(currentPlayerId)) {
                playerNames.add(player);
            }
        }

        if (playerNames.size() > 1) {
            return playerNames.get(0) + ", " + playerNames.get(1) + " + " + (playerNames.size() - 1);
        } else {
            return playerNames.size() == 0 ? "solo" : playerNames.get(0);
        }
    }

    public boolean isAllMissionsCompletedByPlayer(String playerId) {
        return missionResults.stream()
                .filter(mr -> mr.getPlayerId().equals(playerId))
                .count() == missions.size();
    }

    public boolean isMissionCompletedByAllPlayers(String missionId) {
        for (String player : getPlayers()) {
            MissionResult result = getMissionResultForPlayer(player, missionId);
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

    @DynamoDBIgnore
    @JsonIgnore
    public int getNumberOfMissions() {
        return missions.size();
    }


    @DynamoDBIgnore
    @JsonIgnore
    public int getCurrentMissionNumber(String playerId) {
        return (int) (missionResults.stream().filter(mr -> mr.getPlayerId().equals(playerId)).count() + 1);
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public List<MissionResult> getMissionResults() {
        return missionResults;
    }

    public void setMissionResults(List<MissionResult> missionResults) {
        this.missionResults = missionResults;
    }

    public int getPlayerRank(String playerId) {
        return -1;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
