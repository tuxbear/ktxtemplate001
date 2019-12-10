package com.tuxbear.dinos.services.impl;

import com.badlogic.gdx.*;
import com.tuxbear.dinos.services.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 02/01/14 Time: 14:58 To change this template use File | Settings | File
 * Templates.
 */
public class LibGdxLogger implements Logger {
    @Override
    public void log(String message) {
        Gdx.app.log("DINOS", message);
    }
}
