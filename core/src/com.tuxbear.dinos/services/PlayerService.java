package com.tuxbear.dinos.services;

import com.tuxbear.dinos.domain.user.CognitoTokens;
import com.tuxbear.dinos.domain.user.Player;

import java.io.IOException;

public interface PlayerService {

    void login(String username, String password, ServerCallback<CognitoTokens> callback) throws IOException;

    boolean isAuthenticated();

    void refreshToken(ServerCallback<CognitoTokens> callback) throws IOException;

    Player getCurrentPlayer() throws IOException;
    Player getPlayerByUsername(String username);

    void addFriend(Player addTo, String username);
    void blockPlayer(Player addTo, String blockedUsername);
}
