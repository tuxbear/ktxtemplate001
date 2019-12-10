package com.tuxbear.dinos.utils;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Date;

/**
 * Created by tuxbear on 08/12/14.
 */
public class SerializableDate extends Date implements Json.Serializable {

    public SerializableDate() {
        super();
    }

    @Override
    public void write(Json json) {
        json.writeValue("timestamp", this.getTime());
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        long timestamp = jsonData.child().asLong();
        setTime(timestamp);
    }
}
