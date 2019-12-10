package com.tuxbear.dinos.services.events;

import com.tuxbear.dinos.domain.events.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 23/12/13 Time: 14:18 To change this template use File | Settings | File
 * Templates.
 */
public interface EventBus {

    void subscribe(GameEventListener listener, Class<? extends GameEvent>... eventTypes);
    void unsubscribe(GameEventListener listener, Class<? extends GameEvent>... eventTypes);
    void dropAllListenersForEvents(Class<? extends GameEvent>... eventTypes);
    void publishEvent(GameEvent event);

    void dropAllListeners();
}
