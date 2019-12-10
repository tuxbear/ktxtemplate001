package com.tuxbear.dinos.services.fakes;

import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.domain.user.Stats;
import com.tuxbear.dinos.services.PlayerService;
import com.tuxbear.dinos.utils.SerializableDate;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Ole - André Johansen on 05.01.14.
 */
public class FakePlayerService implements PlayerService {

    private Player currentPlayer;

    public FakePlayerService() {
        this.currentPlayer = new Player();
        this.currentPlayer.setId("tuxbear");
        this.currentPlayer.setFriendIds(Arrays.asList("eleni", "dunderjens"));
        this.currentPlayer.setBlocked(Arrays.asList("dickhead"));
        this.currentPlayer.setXp(1337);
        this.currentPlayer.setLastSeen(new SerializableDate());
    }

    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public Player getPlayerByUsername(String username) {
        return currentPlayer;
    }

    @Override
    public Stats getPlayerStats(String username) {
        return new Stats();
    }

    @Override
    public void addFriend(Player addTo, String username) {

    }

    @Override
    public void blockPlayer(Player addTo, String blockedUsername) {

    }
}
