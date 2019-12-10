package com.tuxbear.dinos.services;

import com.tuxbear.dinos.domain.game.MissionResult;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.services.impl.AzureGameServiceAdapter.responses.GameEventUpdatesResponse;

import java.util.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 08/12/13 Time: 13:06 To change this template use File | Settings | File
 * Templates.
 */
public interface GameService {
    public void createGameAsync(String username, List<String> players, String board, int rounds, String difficulty,
                                final ServerCallback<MultiplayerGame> responseCallback);

    void getGameByIdAsync(String id, ServerCallback<MultiplayerGame> responseCallback);

    void getActiveGamesForPlayerAsync(Player player, ServerCallback<List> responseCallback);

    void reportRoundResultsAsync(MissionResult result, ServerCallback<MultiplayerGame> responseCallback);

    public void getUpdatesAsync(Date since, ServerCallback<GameEventUpdatesResponse> responseCallback);
}
