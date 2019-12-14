package com.tuxbear.dinos.services;

import com.tuxbear.dinos.domain.game.MissionResult;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.domain.user.Player;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DataService {

    void getPlayerProfile(final ServerCallback<Player> responseCallback) throws IOException;

    public void createGameAsync(String username, List<String> players, String board, int rounds, String difficulty,
                                final ServerCallback<MultiplayerGame> responseCallback) throws IOException;

    void getGameByIdAsync(String id, ServerCallback<MultiplayerGame> responseCallback) throws IOException;

    void getActiveGamesForPlayerAsync(Player player, ServerCallback<List<MultiplayerGame>> responseCallback) throws IOException;

    void reportRoundResultsAsync(MissionResult result, ServerCallback<MultiplayerGame> responseCallback) throws IOException;

    public void getUpdatesAsync(Date since, ServerCallback<com.tuxbear.dinos.services.impl.aws.responses.GameEventUpdatesResponse> responseCallback) throws IOException;
}
