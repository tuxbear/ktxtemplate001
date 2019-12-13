package com.tuxbear.dinos.services.impl;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tuxbear.dinos.domain.user.Player;
import com.tuxbear.dinos.integrations.CognitoClient;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.LocalStorage;
import com.tuxbear.dinos.services.PlayerService;

/**
 * Created by tuxbear on 08/12/14.
 */
public class PlayerServiceImpl implements PlayerService {

    LocalStorage storage = IoC.resolve(LocalStorage.class);


    @Override
    public Player login(String username, String password) {
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

        Player player = new Player();
        player.setId(username);

        return player;

    }

    @Override
    public Player getCurrentPlayer() {
        return null;
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
