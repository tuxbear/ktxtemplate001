package com.tuxbear.dinos.services;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 07/01/14 Time: 11:12 To change this template use File | Settings | File
 * Templates.
 */
public interface ServerCallback<T> {

    void processResult(T result, ServerCallResults status);

}
