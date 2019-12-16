package com.tuxbear.dinos.domain.dto.requests;

import java.util.*;

public class GetUpdatesRequest {
    private Date since;

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }
}