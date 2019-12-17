package com.tuxbear.dinos.services.impl.aws;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.badlogic.gdx.*;
import com.badlogic.gdx.net.*;
import com.badlogic.gdx.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.services.Logger;

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
        logger.log("Received remote response: " + resultAsString);
        if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            try {
                final T receivedObject = jsonSerializer.readValue(resultAsString, returnType);
                Gdx.app.postRunnable(() -> {
                    try {
                        callback.processResult(receivedObject, ServerCallResults.success());
                    } catch (Exception e) {
                        e.printStackTrace();
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
        AuthenticationResultType authenticationResultType = playerService.refreshToken();
        hasTriedRefreshToken = true;
        if (authenticationResultType != null) {
            request.setHeader("Authorization", authenticationResultType.getIdToken());
            NetJavaImpl netClient = new NetJavaImpl();
            netClient.sendHttpRequest(request, this);
        } else {
            callbackWithLoginRequired();
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
        Gdx.app.postRunnable(() -> {
            try {
                callback.processResult(null, ServerCallResults.failure(errorString));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void callbackWithLoginRequired() {
        Gdx.app.postRunnable(() -> {
            try {
                callback.processResult(null, ServerCallResults.loginRequired());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
