package com.tuxbear.dinos.services.impl.aws;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.net.NetJavaImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuxbear.dinos.domain.user.CognitoTokens;
import com.tuxbear.dinos.services.IoC;
import com.tuxbear.dinos.services.Logger;
import com.tuxbear.dinos.services.PlayerService;
import com.tuxbear.dinos.services.ServerCallResults;
import com.tuxbear.dinos.services.ServerCallback;

import java.io.IOException;

public class ServerCallbackAdapter<T> implements Net.HttpResponseListener {

    private Net.HttpRequest request;
    private final ServerCallback<T> callback;
    private final Class<T> returnType;
    private final ObjectMapper jsonSerializer = IoC.resolve(ObjectMapper.class);
    private final PlayerService playerService = IoC.resolve(PlayerService.class);

    private boolean hasTriedRefreshToken = false;

    private final Logger logger = IoC.resolve(Logger.class);

    public ServerCallbackAdapter(Net.HttpRequest request, Class<T> returnType, ServerCallback<T> callback) {
        this.request = request;
        this.callback = callback;
        this.returnType = returnType;
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        String resultAsString = httpResponse.getResultAsString();
        logger.log("Received remote response: HTTP(" + httpResponse.getStatus().getStatusCode() + ") -> '" + resultAsString + "'");
        if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            try {
                final T receivedObject = jsonSerializer.readValue(resultAsString, returnType);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callback.processResult(receivedObject, ServerCallResults.success());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (IOException e) {
                callbackWithFailure("failed to deserialize: " + e.getMessage());
            }
        } else if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_UNAUTHORIZED && !hasTriedRefreshToken) {
                refreshTokenAndRetry();
        } else {
            callbackWithFailure(resultAsString);
        }
    }

    private void refreshTokenAndRetry() {
        hasTriedRefreshToken = true;

        try {
            playerService.refreshToken(new ServerCallback<CognitoTokens>() {
                @Override
                public void processResult(CognitoTokens result, ServerCallResults status) throws Exception {
                    if (result != null) {
                        request.setHeader("Authorization", result.getIdToken());
                        NetJavaImpl netClient = new NetJavaImpl();
                        netClient.sendHttpRequest(request, ServerCallbackAdapter.this);
                    } else {
                        ServerCallbackAdapter.this.callbackWithLoginRequired();
                    }
                }
            });
        } catch (IOException e) {
            callbackWithFailure(e.getMessage());
        }
    }

    @Override
    public void failed(Throwable t) {
        callbackWithFailure(t.toString());
    }

    @Override
    public void cancelled() {
        callbackWithFailure("Cancelled");
    }

    private void callbackWithFailure(final String errorString) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.processResult(null, ServerCallResults.failure(errorString));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void callbackWithLoginRequired() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.processResult(null, ServerCallResults.loginRequired());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
