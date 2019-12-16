package com.tuxbear.dinos.domain.dto.requests;

public class GameRequest {

    private String id;

    public GameRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
