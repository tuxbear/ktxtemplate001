package com.tuxbear.dinos.services;

import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.domain.user.*;

import java.util.*;

/**
 * User: tuxbear Date: 28/01/14 Time: 12:20
 */
public interface LocalStorage {

    void saveGames(List<MultiplayerGame> games);
    void saveChatMessages(List<ChatMessage> messages);
    void saveCurrentUser(Player player);
    void saveAccessToken(String token);

    List<MultiplayerGame> getAllGames();
    List<ChatMessage> getAllChatMessagesForGame(String gameId);
    Player getCurrentUser();
    String getCurrentAccessToken();
}
