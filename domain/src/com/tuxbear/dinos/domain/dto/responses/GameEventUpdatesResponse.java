package com.tuxbear.dinos.services.impl.aws.responses;

import com.tuxbear.dinos.domain.game.*;

import java.util.*;

public class GameEventUpdatesResponse {

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<MissionResult> getNewMissionResults() {
        return newMissionResults;
    }

    public void setNewMissionResults(List<MissionResult> newMissionResults) {
        this.newMissionResults = newMissionResults;
    }

    private Date timestamp;
    private List<MissionResult> newMissionResults;
}