package com.tuxbear.dinos.services.impl;

import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.domain.user.Stats;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.LocalStorage;
import com.tuxbear.dinos.services.PlayerService;

/**
 * Created by tuxbear on 08/12/14.
 */
public class PlayerServiceImpl implements PlayerService {

    LocalStorage storage = IoC.resolve(LocalStorage.class);


    @Override
    public Player getCurrentPlayer() {
        return null;
    }

    @Override
    public Player getPlayerByUsername(String username) {
        return null;
    }

    @Override
    public Stats getPlayerStats(String username) {
        return null;
    }

    @Override
    public void addFriend(Player addTo, String username) {

    }

    @Override
    public void blockPlayer(Player addTo, String blockedUsername) {

    }
}
