package com.tuxbear.dinos.services.impl;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.integrations.CognitoClient;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.LocalStorage;
import com.tuxbear.dinos.services.PlayerService;

import java.io.IOException;
import java.util.Date;

public class PlayerServiceImpl implements PlayerService {

    LocalStorage storage = IoC.resolve(LocalStorage.class);

    @Override
    public AuthenticationResultType login(String username, String password) {
        CognitoClient cognitoClient = new CognitoClient();
        AuthenticationResultType answer;

        try {
            cognitoClient.register(username, password);
            answer = cognitoClient.login(username, password);
        } catch(UsernameExistsException exists) {
            answer = cognitoClient.login(username, password);

        }

        try {
            storage.saveAccessToken(answer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error saving to disk");
        }

        return answer;
    }

    @Override
    public boolean isAuthenticated() {
        try {
            AuthenticationResultType token = storage.getCurrentAccessToken();
            boolean tokenExpired = JWT.decode(token.getIdToken()).getExpiresAt().after(new Date());
            return !tokenExpired;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public AuthenticationResultType refreshToken() {
        CognitoClient cognitoClient = new CognitoClient();
        try {
            AuthenticationResultType token = storage.getCurrentAccessToken();
            AuthenticationResultType newToken = cognitoClient.refreshAccessToken(token.getRefreshToken());
            storage.saveAccessToken(newToken);
            return newToken;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Player getCurrentPlayer() {
        try {
            return storage.getCurrentUser();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Player getPlayerByUsername(String username) {
        return null;
    }

    @Override
    public void addFriend(Player addTo, String username) {

    }

    @Override
    public void blockPlayer(Player addTo, String blockedUsername) {

    }
}
