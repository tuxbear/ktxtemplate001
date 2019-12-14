package com.tuxbear.dinos.services;

public interface ServerCallback<T> {
    void processResult(T result, ServerCallResults status);
}
