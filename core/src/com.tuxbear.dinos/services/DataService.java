package com.tuxbear.dinos.services;

import com.tuxbear.dinos.domain.dto.requests.LoginOrRegisterRequest;
import com.tuxbear.dinos.domain.dto.responses.GameEventUpdatesResponse;
import com.tuxbear.dinos.domain.game.MissionResult;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.domain.user.CognitoTokens;
import com.tuxbear.dinos.domain.user.Player;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DataService {

    void loginOrRegister(LoginOrRegisterRequest auth, ServerCallback<CognitoTokens> responseCallback) throws IOException;

    void refreshToken(CognitoTokens tokens, ServerCallback<CognitoTokens> responseCallback) throws IOException;

    void getPlayerProfile(final ServerCallback<Player> responseCallback) throws Exception;

    public void createGameAsync(String username, List<String> players, String board, int rounds, String difficulty,
                                final ServerCallback<MultiplayerGame> responseCallback) throws Exception;

    void getGameByIdAsync(String id, ServerCallback<MultiplayerGame> responseCallback) throws IOException;

    void getActiveGamesAsync(ServerCallback<MultiplayerGame[]> responseCallback) throws Exception;

    void reportRoundResultsAsync(MissionResult result, ServerCallback<MultiplayerGame> responseCallback) throws IOException;

    public void getUpdatesAsync(Date since, ServerCallback<GameEventUpdatesResponse> responseCallback) throws IOException;
}
