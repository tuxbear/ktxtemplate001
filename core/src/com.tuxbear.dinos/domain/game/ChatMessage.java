package com.tuxbear.dinos.domain.game;

/**
 * User: tuxbear Date: 28/01/14 Time: 12:21
 */
public class ChatMessage {

    private String gameId;

    private String userId;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
