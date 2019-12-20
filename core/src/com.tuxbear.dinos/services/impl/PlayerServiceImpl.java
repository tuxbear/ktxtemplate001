package com.tuxbear.dinos.services.impl;

import com.auth0.jwt.JWT;
import com.tuxbear.dinos.domain.dto.requests.LoginOrRegisterRequest;
import com.tuxbear.dinos.domain.user.CognitoTokens;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.services.DataService;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.LocalStorage;
import com.tuxbear.dinos.services.PlayerService;
import com.tuxbear.dinos.services.ServerCallResults;
import com.tuxbear.dinos.services.ServerCallStatus;
import com.tuxbear.dinos.services.ServerCallback;

import java.io.IOException;
import java.util.Date;

public class PlayerServiceImpl implements PlayerService {

    LocalStorage storage = IoC.resolve(LocalStorage.class);
    DataService data = IoC.resolve(DataService.class);

    @Override
    public void login(String username, String password, ServerCallback<CognitoTokens> callback) throws IOException {
        LoginOrRegisterRequest request = new LoginOrRegisterRequest();
        request.setUsername(username);
        request.setPassword(password);
        data.loginOrRegister(request, new ServerCallback<CognitoTokens>() {
            @Override
            public void processResult(CognitoTokens result, ServerCallResults status) throws Exception {
                if (status.getStatus().equals(ServerCallStatus.SUCCESS)) {
                    storage.saveAccessToken(result);
                }
                callback.processResult(result, status);
            }
        });
    }

    @Override
    public boolean isAuthenticated() {
        try {
            CognitoTokens token = storage.getCurrentAccessToken();
            boolean tokenExpired = JWT.decode(token.getIdToken()).getExpiresAt().after(new Date());
            return !tokenExpired;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void refreshToken(ServerCallback<CognitoTokens> callback) throws IOException {
        CognitoTokens token = storage.getCurrentAccessToken();

        data.refreshToken(token, new ServerCallback<CognitoTokens>() {
            @Override
            public void processResult(CognitoTokens result, ServerCallResults status) throws Exception {
                if (status.getStatus().equals(ServerCallStatus.SUCCESS)) {
                    storage.saveAccessToken(result);
                }
                callback.processResult(result, status);
            }
        });
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
