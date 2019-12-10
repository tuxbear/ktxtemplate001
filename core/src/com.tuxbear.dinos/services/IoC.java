package com.tuxbear.dinos.services;

import com.badlogic.gdx.utils.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.services.events.*;
import com.tuxbear.dinos.services.fakes.*;
import com.tuxbear.dinos.services.impl.AzureGameServiceAdapter.*;
import com.tuxbear.dinos.services.impl.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 02/01/14 Time: 12:30 To change this template use File | Settings | File
 * Templates.
 */
public class IoC {

    static Map<Class<?>, Object> singletons = new HashMap<Class<?>, Object>();
    static Map<Class<?>, Class<?>> typeMappings = new HashMap<Class<?>, Class<?>>();

    static {

        singletons.put(Json.class, initializeJsonParser());
        singletons.put(ScoreService.class, new StandardScoreService());
        singletons.put(Logger.class, new LibGdxLogger());
        singletons.put(EventBus.class, new EventBusImpl());
        singletons.put(PlayerService.class, new FakePlayerService());
        singletons.put(GameService.class, new FakeGameService());

    }

    private static Json initializeJsonParser() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setIgnoreUnknownFields(true);

        json.setSerializer(Direction.class, new Json.Serializer<Direction>() {
            @Override
            public void write(Json json, Direction object, Class knownType) {
                json.writeValue(object.name());
            }

            @Override
            public Direction read(Json json, JsonValue jsonData, Class type) {
                return Direction.valueOf(jsonData.asString());
            }
        });

        return json;
    }


    public static <T> T resolve(Class<T> serviceType) {
        if (singletons.containsKey(serviceType)) {
            return (T) singletons.get(serviceType);
        } else if (typeMappings.containsKey(serviceType)) {
            Class<T> implType = (Class<T>) typeMappings.get(serviceType);
            try {
                return implType.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        throw new IllegalArgumentException();
    }
}
