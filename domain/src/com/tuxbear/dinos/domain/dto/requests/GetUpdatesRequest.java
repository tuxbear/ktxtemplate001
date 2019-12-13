/**
 * Copyright (c) 2012-2013 Embriq AS. All rights reserved.
 *
 * Created 15/02/14 16:01
 * @author tuxbear
 */
package com.tuxbear.dinos.services.impl.aws.requests;

import java.util.*;

public class GetUpdatesRequest {

    private String playerId;

    private Date since;

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}