package com.tuxbear.dinos.services;

import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.domain.user.Stats;

/**
 * Created by Ole - André Johansen on 04.01.14.
 */
public interface PlayerService {

    Player getCurrentPlayer();
    Player getPlayerByUsername(String username);
    Stats getPlayerStats(String username);

    void addFriend(Player addTo, String username);
    void blockPlayer(Player addTo, String blockedUsername);
}
