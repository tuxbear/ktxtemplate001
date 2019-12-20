package com.tuxbear.dinos.services.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuxbear.dinos.domain.game.MultiplayerGame;
import com.tuxbear.dinos.domain.user.CognitoTokens;
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

    private final String userSave = "userSave";
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
        Preferences userSave = Gdx.app.getPreferences(this.userSave);

        String playerJsonString = jsonSerializer.writeValueAsString(player);
        userSave.putString("currentPlayer", playerJsonString);
        userSave.flush();
    }

    @Override
    public void saveAccessToken(CognitoTokens token) throws JsonProcessingException {
        Preferences userSave = Gdx.app.getPreferences(this.userSave);

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
        Preferences userSave = Gdx.app.getPreferences(this.userSave);
        String playerJsonString = userSave.getString("currentPlayer");
        return playerJsonString == null || playerJsonString == "" ? null : jsonSerializer.readValue(playerJsonString, Player.class);
    }

    @Override
    public CognitoTokens getCurrentAccessToken() throws IOException {
        Preferences userSave = Gdx.app.getPreferences(this.userSave);
        String token = userSave.getString("token");
        return token == null || token == "" ? null : jsonSerializer.readValue(token, CognitoTokens.class);
    }
}
