/**
 * Copyright (c) 2012-2013 Embriq AS. All rights reserved.
 *
 * Created 15/02/14 15:51
 * @author tuxbear
 */
package com.tuxbear.dinos.services.impl.AzureGameServiceAdapter.responses;

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

    public List<ChatMessage> getNewComments() {
        return newComments;
    }

    public void setNewComments(List<ChatMessage> newComments) {
        this.newComments = newComments;
    }

    private Date timestamp;
    private List<MissionResult> newMissionResults;
    private List<ChatMessage> newComments;

}