package com.tuxbear.dinos.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.tuxbear.dinos.services.events.*;
import com.tuxbear.dinos.services.impl.*;
import com.tuxbear.dinos.services.impl.aws.AWSDataService;

import java.util.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 02/01/14 Time: 12:30 To change this template use File | Settings | File
 * Templates.
 */
public class IoC {

    static Map<Class<?>, Object> singletons = new HashMap<Class<?>, Object>();
    static Map<Class<?>, Class<?>> typeMappings = new HashMap<Class<?>, Class<?>>();

    private static final ObjectMapper mapper = new ObjectMapper();

    static {

        mapper.registerModule(new KotlinModule());

        singletons.put(ObjectMapper.class, mapper);
        singletons.put(ScoreService.class, new StandardScoreService());
        singletons.put(Logger.class, new DummyLogger());
        singletons.put(EventBus.class, new EventBusImpl());
        singletons.put(LocalStorage.class, new LocalStorageImpl());
        singletons.put(PlayerService.class, new PlayerServiceImpl());
        singletons.put(DataService.class, new AWSDataService());

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
