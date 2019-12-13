package com.tuxbear.dinos.services;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.domain.user.*;

import java.io.IOException;
import java.util.*;

/**
 * User: tuxbear Date: 28/01/14 Time: 12:20
 */
public interface LocalStorage {

    void saveGames(List<MultiplayerGame> games) throws JsonProcessingException;
    void saveCurrentUser(Player player) throws JsonProcessingException;
    void saveAccessToken(AuthenticationResultType token) throws JsonProcessingException;

    List<MultiplayerGame> getAllGames() throws IOException;
    Player getCurrentUser() throws IOException;
    AuthenticationResultType getCurrentAccessToken() throws IOException;
}
