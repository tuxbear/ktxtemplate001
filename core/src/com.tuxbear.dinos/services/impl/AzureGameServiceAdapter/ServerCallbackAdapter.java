package com.tuxbear.dinos.services.impl.AzureGameServiceAdapter;

import com.badlogic.gdx.*;
import com.badlogic.gdx.net.*;
import com.badlogic.gdx.utils.*;
import com.tuxbear.dinos.services.*;
import com.tuxbear.dinos.services.Logger;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 07/01/14 Time: 15:02 To change this template use File | Settings | File
 * Templates.
 */
public class ServerCallbackAdapter<T> implements Net.HttpResponseListener {

    private final ServerCallback<T> callback;
    private final Class<T> returnType;
    private final Json jsonSerializer = IoC.resolve(Json.class);
    private final Logger logger = IoC.resolve(Logger.class);

    public ServerCallbackAdapter(Class<T> returnType, ServerCallback<T> callback) {
        this.callback = callback;
        this.returnType = returnType;
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        logger.log("Recevied remote response: " + httpResponse.toString());
        if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            final T receivedObject = jsonSerializer.fromJson(returnType, httpResponse.getResultAsString());
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    callback.processResult(receivedObject, ServerCallResults.success());
                }
            });
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
