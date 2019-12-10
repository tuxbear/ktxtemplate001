package com.tuxbear.dinos.services.impl.AzureGameServiceAdapter;

import com.badlogic.gdx.*;
import com.badlogic.gdx.net.*;
import com.badlogic.gdx.utils.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.domain.user.*;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.services.impl.AzureGameServiceAdapter.mappers.*;
import com.tuxbear.dinos.services.impl.AzureGameServiceAdapter.requests.*;
import com.tuxbear.dinos.services.impl.AzureGameServiceAdapter.responses.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 07/01/14 Time: 10:59 To change this template use File | Settings | File
 * Templates.
 */
public class AzureGameService implements GameService {

    String endpointBaseUrl = "http://dinos.azurewebsites.net/api/multiplayerapi";
//    String endpointBaseUrl = "http://localhost:50395/api/multiplayerapi";

    String createGameUrl = endpointBaseUrl + "/startgame";
    String getGameUrl = endpointBaseUrl + "/getGame";
    String getActiveGamesForUserUrl = endpointBaseUrl + "/getactivegames";
    String reportMissionResultUrl = endpointBaseUrl + "/ReportMissionResult";
    String getUpdatesUrl = endpointBaseUrl + "/GetUpdates";

    private int defaultTimeoutMs = 30 * 1000;

    private final Json jsonSerializer = IoC.resolve(Json.class);

    private final NetJavaImpl netClient;

    public AzureGameService() {
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

        GameRequest getGameRequest = new GameRequest(id);
        Net.HttpRequest request = createHttpPostRequest(createGameUrl, getGameRequest);
        netClient.sendHttpRequest(request, new ServerCallbackAdapter<>(MultiplayerGame.class, responseCallback));
    }

    @Override
    public void getActiveGamesForPlayerAsync(Player player, final ServerCallback<List> responseCallback) {
        ActiveGameListRequest getActiveGamesRequest = new ActiveGameListRequest(player.getId());
        Net.HttpRequest request = createHttpPostRequest(getActiveGamesForUserUrl, getActiveGamesRequest);
        netClient.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String responseString = httpResponse.getResultAsString();
                final List games = new MultiplayerGameJSONMapper().fromJson(responseString);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        responseCallback.processResult(games, new ServerCallResults(ServerCallStatus.SUCCESS, ""));
                    }
                });
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
    public void getUpdatesAsync(Date since, ServerCallback<GameEventUpdatesResponse> responseCallback) {
        Net.HttpRequest request = createHttpPostRequest(getUpdatesUrl, since);
        netClient.sendHttpRequest(request, new ServerCallbackAdapter<>(GameEventUpdatesResponse.class, responseCallback));
    }

    private Net.HttpRequest createHttpPostRequest(String url, Object payloadObject) {
        Net.HttpRequest request = new Net.HttpRequest("POST");
        request.setHeader("Content-Type", "text/json");
        String jsonString = jsonSerializer.toJson(payloadObject);
        request.setContent(jsonString);
        request.setUrl(url);
        request.setTimeOut(defaultTimeoutMs);
        return request;
    }
}
