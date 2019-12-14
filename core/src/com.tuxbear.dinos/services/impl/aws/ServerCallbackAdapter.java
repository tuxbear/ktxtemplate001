package com.tuxbear.dinos.services.impl.aws;

import com.badlogic.gdx.*;
import com.badlogic.gdx.net.*;
import com.badlogic.gdx.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.services.Logger;

import java.io.IOException;

public class ServerCallbackAdapter<T> implements Net.HttpResponseListener {

    private final ServerCallback<T> callback;
    private final Class<T> returnType;
    private final ObjectMapper jsonSerializer = IoC.resolve(ObjectMapper.class);
    private final Logger logger = IoC.resolve(Logger.class);

    public ServerCallbackAdapter(Class<T> returnType, ServerCallback<T> callback) {
        this.callback = callback;
        this.returnType = returnType;
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        logger.log("Recevied remote response: " + httpResponse.toString());
        if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            try {
                final T receivedObject = jsonSerializer.readValue(httpResponse.getResultAsString(), returnType);
                Gdx.app.postRunnable(() -> callback.processResult(receivedObject, ServerCallResults.success()));
            } catch (IOException e) {
                callbackWithFailure("failed to deserialize: " + e.getMessage());
            }
        } else {
            callbackWithFailure(httpResponse.getResultAsString());
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
                callback.processResult(null, ServerCallResults.failure(errorString));
            }
        });
    }
}
