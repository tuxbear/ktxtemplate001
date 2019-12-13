package com.tuxbear.dinos.services.impl;

import com.badlogic.gdx.Gdx;
import com.tuxbear.dinos.services.*;

public class DummyLogger implements Logger {
    @Override
    public void log(String message) {
        Gdx.app.log("DINOS", message);
    }
}
