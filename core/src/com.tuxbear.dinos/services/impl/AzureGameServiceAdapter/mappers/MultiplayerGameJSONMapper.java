package com.tuxbear.dinos.services.impl.AzureGameServiceAdapter.mappers;

import com.badlogic.gdx.utils.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.services.IoC;

import java.util.*;

/**
 * User: tuxbear Date: 15/01/14 Time: 18:27
 */
public class MultiplayerGameJSONMapper{

    public List<MultiplayerGame> fromJson(String jsonString) {

        Json json = IoC.resolve(Json.class);
        MultiplayerGame[] result = json.fromJson(MultiplayerGame[].class, jsonString);

        return Arrays.asList(result);
    }
}