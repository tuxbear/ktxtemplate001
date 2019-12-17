package com.tuxbear.dinos.services;

import com.tuxbear.dinos.domain.game.MissionResult;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.domain.user.Player;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DataService {

    void getPlayerProfile(final ServerCallback<Player> responseCallback) throws Exception;

    public void createGameAsync(String username, List<String> players, String board, int rounds, String difficulty,
                                final ServerCallback<MultiplayerGame> responseCallback) throws Exception;

    void getGameByIdAsync(String id, ServerCallback<MultiplayerGame> responseCallback) throws IOException;

    void getActiveGamesAsync(ServerCallback<MultiplayerGame[]> responseCallback) throws Exception;

    void reportRoundResultsAsync(MissionResult result, ServerCallback<MultiplayerGame> responseCallback) throws IOException;

    public void getUpdatesAsync(Date since, ServerCallback<com.tuxbear.dinos.services.impl.aws.responses.GameEventUpdatesResponse> responseCallback) throws IOException;
}
