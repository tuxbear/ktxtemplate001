package com.tuxbear.dinos.services.impl.AzureGameServiceAdapter.requests;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 07/01/14 Time: 16:56 To change this template use File | Settings | File
 * Templates.
 */
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
