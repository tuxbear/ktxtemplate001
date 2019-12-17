package com.tuxbear.dinos.services;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.tuxbear.dinos.domain.user.Player;

import java.io.IOException;

public interface PlayerService {

    AuthenticationResultType login(String username, String password);

    boolean isAuthenticated();

    AuthenticationResultType refreshToken();

    Player getCurrentPlayer() throws IOException;
    Player getPlayerByUsername(String username);

    void addFriend(Player addTo, String username);
    void blockPlayer(Player addTo, String blockedUsername);
}
