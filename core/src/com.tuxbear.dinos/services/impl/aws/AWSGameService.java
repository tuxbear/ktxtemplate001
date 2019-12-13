package com.tuxbear.dinos.services.impl.aws;

import com.badlogic.gdx.*;
import com.badlogic.gdx.net.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.domain.user.*;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.services.impl.aws.requests.GameRequest;
import com.tuxbear.dinos.services.impl.aws.requests.NewGameRequest;
import com.tuxbear.dinos.services.impl.aws.responses.GameEventUpdatesResponse;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 07/01/14 Time: 10:59 To change this template use File | Settings | File
 * Templates.
 */
public class AWSGameService implements GameService {

    String endpointBaseUrl = "http://dinos.azurewebsites.net/api/multiplayerapi";
//    String endpointBaseUrl = "http://localhost:50395/api/multiplayerapi";

    String createGameUrl = endpointBaseUrl + "/startgame";
    String getGameUrl = endpointBaseUrl + "/getGame";
    String getActiveGamesForUserUrl = endpointBaseUrl + "/getactivegames";
    String reportMissionResultUrl = endpointBaseUrl + "/ReportMissionResult";
    String getUpdatesUrl = endpointBaseUrl + "/GetUpdates";

    private int defaultTimeoutMs = 30 * 1000;

    private final ObjectMapper jsonSerializer = IoC.resolve(ObjectMapper.class);

    private final NetJavaImpl netClient;

    public AWSGameService() {
        netClient = new NetJavaImpl();
    }

    @Override
    public void createGameAsync(String username, List<String> players, String board, int rounds, String difficulty,
                                           final ServerCallback<MultiplayerGame> responseCallback) {

        NewGameRequest createGameRequest = new NewGameRequest(username, board, players, rounds, difficulty);
        Net.HttpRequest request = createHttpPostRequest(createGameUrl, createGameRequest);
        netClient.sendHttpRequest(request, new ServerCallbackAdapter<>(MultiplayerGame.class, responseCallback));
    }

    @Override
    public void getGameByIdAsync(String id, final ServerCallback<MultiplayerGame> responseCallback) {

        com.tuxbear.dinos.services.impl.aws.requests.GameRequest getGameRequest = new GameRequest(id);
        Net.HttpRequest request = createHttpPostRequest(createGameUrl, getGameRequest);
        netClient.sendHttpRequest(request, new ServerCallbackAdapter<>(MultiplayerGame.class, responseCallback));
    }

    @Override
    public void getActiveGamesForPlayerAsync(Player player, final ServerCallback<List<MultiplayerGame>> responseCallback) {
        com.tuxbear.dinos.services.impl.aws.requests.ActiveGameListRequest getActiveGamesRequest = new com.tuxbear.dinos.services.impl.aws.requests.ActiveGameListRequest(player.getId());
        Net.HttpRequest request = createHttpPostRequest(getActiveGamesForUserUrl, getActiveGamesRequest);
        netClient.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String responseString = httpResponse.getResultAsString();
                try {
                    final List<MultiplayerGame> games = Arrays.asList(jsonSerializer.readValue(responseString, MultiplayerGame[].class));
                    Gdx.app.postRunnable(() ->
                            responseCallback.processResult(games, new ServerCallResults(ServerCallStatus.SUCCESS, ""))
                    );

                } catch (IOException e) {
                    System.out.println(e);
                }
            }

            @Override
            public void failed(Throwable t) {
                int i = 0;
            }

            @Override
            public void cancelled() {

            }
        });
    }


    @Override
    public void reportRoundResultsAsync(MissionResult result, ServerCallback<MultiplayerGame> serverCallback) {
        Net.HttpRequest request = createHttpPostRequest(reportMissionResultUrl, result);
        netClient.sendHttpRequest(request, new ServerCallbackAdapter<>(MultiplayerGame.class, serverCallback));
    }

    @Override
    public void getUpdatesAsync(Date since, ServerCallback<com.tuxbear.dinos.services.impl.aws.responses.GameEventUpdatesResponse> responseCallback) {
        Net.HttpRequest request = createHttpPostRequest(getUpdatesUrl, since);
        netClient.sendHttpRequest(request, new ServerCallbackAdapter<>(GameEventUpdatesResponse.class, responseCallback));
    }

    private Net.HttpRequest createHttpPostRequest(String url, Object payloadObject) {
        Net.HttpRequest request = new Net.HttpRequest("POST");
        request.setHeader("Content-Type", "text/json");
        String jsonString = null;
        try {
            jsonString = jsonSerializer.writeValueAsString(payloadObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        request.setContent(jsonString);
        request.setUrl(url);
        request.setTimeOut(defaultTimeoutMs);
        return request;
    }
}
