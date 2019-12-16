package com.tuxbear.dinos.services.impl.aws;

import com.badlogic.gdx.*;
import com.badlogic.gdx.net.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuxbear.dinos.domain.dto.requests.GameRequest;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.domain.user.*;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.domain.dto.requests.NewGameRequest;
import com.tuxbear.dinos.services.impl.aws.responses.GameEventUpdatesResponse;

import java.io.IOException;
import java.util.*;

public class AWSDataService implements DataService {


    String endpointBaseUrl = "https://c5kr85odi9.execute-api.eu-central-1.amazonaws.com/Prod";

    String getPlayerProfileEndpoint = endpointBaseUrl + "/profile";
    String createGameUrl = endpointBaseUrl + "/new-game";
    String getGameUrl = endpointBaseUrl + "/getGame";
    String getActiveGamesForUserUrl = endpointBaseUrl + "/getactivegames";
    String reportMissionResultUrl = endpointBaseUrl + "/ReportMissionResult";
    String getUpdatesUrl = endpointBaseUrl + "/GetUpdates";

    private int defaultTimeoutMs = 30 * 1000;

    private final ObjectMapper jsonSerializer = IoC.resolve(ObjectMapper.class);
    private final LocalStorage localStorage = IoC.resolve(LocalStorage.class);

    private final NetJavaImpl netClient;

    public AWSDataService() {
        netClient = new NetJavaImpl();
    }

    @Override
    public void getPlayerProfile(final ServerCallback<Player> responseCallback) throws IOException {
        netClient.sendHttpRequest(createHttpGetRequest(getPlayerProfileEndpoint), new ServerCallbackAdapter<>(Player.class, responseCallback));
    }

    @Override
    public void createGameAsync(String username, List<String> players, String board, int rounds, String difficulty,
                                           final ServerCallback<MultiplayerGame> responseCallback) throws IOException {

        NewGameRequest createGameRequest = new NewGameRequest(board, players, rounds, difficulty);
        Net.HttpRequest request = createHttpPostRequest(createGameUrl, createGameRequest);
        netClient.sendHttpRequest(request, new ServerCallbackAdapter<>(MultiplayerGame.class, responseCallback));
    }

    @Override
    public void getGameByIdAsync(String id, final ServerCallback<MultiplayerGame> responseCallback) throws IOException {
        GameRequest getGameRequest = new GameRequest(id);
        Net.HttpRequest request = createHttpPostRequest(createGameUrl, getGameRequest);
        netClient.sendHttpRequest(request, new ServerCallbackAdapter<>(MultiplayerGame.class, responseCallback));
    }

    @Override
    public void getActiveGamesForPlayerAsync(Player player, final ServerCallback<List<MultiplayerGame>> responseCallback) throws IOException {
        responseCallback.processResult(new ArrayList<>(), ServerCallResults.success());
/*
        ActiveGameListRequest getActiveGamesRequest = new ActiveGameListRequest(player.getUsername());
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
        });*/
    }


    @Override
    public void reportRoundResultsAsync(MissionResult result, ServerCallback<MultiplayerGame> serverCallback) throws IOException {
        Net.HttpRequest request = createHttpPostRequest(reportMissionResultUrl, result);
        netClient.sendHttpRequest(request, new ServerCallbackAdapter<>(MultiplayerGame.class, serverCallback));
    }

    @Override
    public void getUpdatesAsync(Date since, ServerCallback<com.tuxbear.dinos.services.impl.aws.responses.GameEventUpdatesResponse> responseCallback) throws IOException {
        Net.HttpRequest request = createHttpPostRequest(getUpdatesUrl, since);
        netClient.sendHttpRequest(request, new ServerCallbackAdapter<>(GameEventUpdatesResponse.class, responseCallback));
    }

    private Net.HttpRequest createHttpPostRequest(String url, Object payloadObject) throws IOException {
        return createHttpRequest(url, "POST", payloadObject);
    }

    private Net.HttpRequest createHttpGetRequest(String url) throws IOException {
        return createHttpRequest(url, "GET", null);
    }

    private Net.HttpRequest createHttpRequest(String url, String httpMethod, Object payloadObject) throws IOException {
        Net.HttpRequest request = new Net.HttpRequest(httpMethod);
        request.setHeader("Authorization", localStorage.getCurrentAccessToken().getIdToken());
        String jsonString = null;
        if (payloadObject != null) {
            try {
                jsonString = jsonSerializer.writeValueAsString(payloadObject);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        request.setContent(jsonString);
        request.setUrl(url);
        request.setTimeOut(defaultTimeoutMs);
        return request;
    }
}
