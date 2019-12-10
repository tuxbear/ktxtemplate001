package com.tuxbear.dinos.services.impl.AzureGameServiceAdapter.requests;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 07/01/14 Time: 17:04 To change this template use File | Settings | File
 * Templates.
 */
public class ActiveGameListRequest {

    private String username;

    public ActiveGameListRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
