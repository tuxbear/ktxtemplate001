package com.tuxbear.dinos.services.impl;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.LocalStorage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ole - André Johansen on 02.02.14.
 */
public class LocalStorageImpl implements LocalStorage {

    ObjectMapper jsonSerializer = IoC.resolve(ObjectMapper.class);

    @Override
    public void saveGames(List<MultiplayerGame> games) throws JsonProcessingException {
        Preferences saveGamePrefs = Gdx.app.getPreferences("gameSave");

        String savedGames = jsonSerializer.writeValueAsString(games);
        saveGamePrefs.putString("allGames", savedGames);
        saveGamePrefs.flush();
    }

    @Override
    public void saveCurrentUser(Player player) throws JsonProcessingException {
        Preferences userSave = Gdx.app.getPreferences("userSave");

        String playerJsonString = jsonSerializer.writeValueAsString(player);
        userSave.putString("currentPlayer", playerJsonString);
        userSave.flush();
    }

    @Override
    public void saveAccessToken(AuthenticationResultType token) throws JsonProcessingException {
        Preferences userSave = Gdx.app.getPreferences("userSave");

        userSave.putString("token", jsonSerializer.writeValueAsString(token));
        userSave.flush();
    }

    @Override
    public List<MultiplayerGame> getAllGames() throws IOException {
        Preferences saveGamePrefs = Gdx.app.getPreferences("gameSave");

        String gamesJsonString = saveGamePrefs.getString("allGames");

        MultiplayerGame[] savedGames = jsonSerializer.readValue(gamesJsonString, MultiplayerGame[].class);

        return Arrays.asList(savedGames);
    }

    @Override
    public Player getCurrentUser() throws IOException {
        Preferences userSave = Gdx.app.getPreferences("userSave");

        String playerJsonString = userSave.getString("currentPlayer");
        Player player = jsonSerializer.readValue(playerJsonString, Player.class);

        return player;
    }

    @Override
    public AuthenticationResultType getCurrentAccessToken() throws IOException {
        Preferences userSave = Gdx.app.getPreferences("userSave");
        return jsonSerializer.readValue(userSave.getString("token"), AuthenticationResultType.class);
    }
}
