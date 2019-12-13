package com.tuxbear.dinos.services;

import com.tuxbear.dinos.domain.user.Player;

public interface PlayerService {

    Player login(String username, String password);

    Player getCurrentPlayer();
    Player getPlayerByUsername(String username);

    void addFriend(Player addTo, String username);
    void blockPlayer(Player addTo, String blockedUsername);
}
