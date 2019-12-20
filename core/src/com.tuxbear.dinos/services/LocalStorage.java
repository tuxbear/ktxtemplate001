package com.tuxbear.dinos.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.domain.user.CognitoTokens;
import com.tuxbear.dinos.domain.user.Player;

import java.io.IOException;
import java.util.List;

/**
 * User: tuxbear Date: 28/01/14 Time: 12:20
 */
public interface LocalStorage {

    void saveGames(List<MultiplayerGame> games) throws JsonProcessingException;
    void saveCurrentUser(Player player) throws JsonProcessingException;
    void saveAccessToken(CognitoTokens token) throws JsonProcessingException;

    List<MultiplayerGame> getAllGames() throws IOException;
    Player getCurrentUser() throws IOException;
    CognitoTokens getCurrentAccessToken() throws IOException;
}
