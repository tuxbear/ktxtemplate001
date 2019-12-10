package com.tuxbear.dinos.services.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.tuxbear.dinos.domain.game.ChatMessage;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.LocalStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ole - André Johansen on 02.02.14.
 */
public class LocalStorageImpl implements LocalStorage {

    Json jsonSerializer = IoC.resolve(Json.class);
    Preferences saveGamePrefs = Gdx.app.getPreferences("gameSave");
    Preferences chats = Gdx.app.getPreferences("chats");
    Preferences userSave = Gdx.app.getPreferences("userSave");
    Preferences appPrefs = Gdx.app.getPreferences("prefs");

    @Override
    public void saveGames(List<MultiplayerGame> games) {
        String savedGames = jsonSerializer.toJson(games);
        saveGamePrefs.putString("allGames", savedGames);
        saveGamePrefs.flush();
    }

    @Override
    public void saveChatMessages(List<ChatMessage> messages) {

        if (messages == null || messages.size() == 0){
            return;
        }

        Map<String, List<ChatMessage>> messagesPergame = new HashMap<>();

        for (ChatMessage message : messages) {
            String gameId = message.getGameId();
            if (messagesPergame.containsKey(gameId)) {
                messagesPergame.get(gameId).add(message);
            } else {
                messagesPergame.put(gameId, new ArrayList<ChatMessage>());
                messagesPergame.get(gameId).add(message);
            }
        }

        for (String gameId : messagesPergame.keySet()) {
            List<ChatMessage> messagesInGame = messagesPergame.get(gameId);
            String gameMessages = jsonSerializer.toJson(messagesInGame);
            saveGamePrefs.putString(gameId, gameMessages);
        }

        saveGamePrefs.flush();
    }

    @Override
    public void saveCurrentUser(Player player) {
        String playerJsonString = jsonSerializer.toJson(player);
        userSave.putString("currentPlayer", playerJsonString);
        userSave.flush();
    }

    @Override
    public void saveAccessToken(String token) {
        userSave.putString("token", token);
        userSave.flush();
    }

    @Override
    public List<MultiplayerGame> getAllGames() {
        String gamesJsonString = saveGamePrefs.getString("allGames");
        List<MultiplayerGame> savedGames = jsonSerializer.fromJson(List.class, gamesJsonString);

        return savedGames;
    }

    @Override
    public List<ChatMessage> getAllChatMessagesForGame(String gameId) {
        String chatMessagesJsonString = chats.getString(gameId);
        List<ChatMessage> savedGames = jsonSerializer.fromJson(List.class, chatMessagesJsonString);

        return savedGames;
    }

    @Override
    public Player getCurrentUser() {
        String playerJsonString = userSave.getString("currentPlayer");
        Player player = jsonSerializer.fromJson(Player.class, playerJsonString);

        return player;
    }

    @Override
    public String getCurrentAccessToken() {
        return userSave.getString("token");
    }
}
