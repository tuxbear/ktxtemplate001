package com.tuxbear.dinos.services.events;

import com.tuxbear.dinos.domain.events.GameEvent;
import com.tuxbear.dinos.domain.events.GameEventListener;
import com.tuxbear.dinos.services.*;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 21/12/13 Time: 16:17 To change this template use File | Settings | File
 * Templates.
 */
public class EventBusImpl implements EventBus {

    HashMap<Class<? extends GameEvent>, List<GameEventListener>> subscriptions = new HashMap<>();

    private final Logger log;

    public EventBusImpl () {
        log = IoC.resolve(Logger.class);
    }

    @Override
    public void subscribe(GameEventListener listener, Class<? extends GameEvent>... eventTypes) {

        List<String> eventNames = getEventNames(eventTypes);

        log.log("Subscription: " + listener.getClass().getSimpleName() + " on " + eventNames);

        for(Class<? extends GameEvent> eventType : eventTypes) {
            if(subscriptions.containsKey(eventType)) {
                List<GameEventListener> subscribers = subscriptions.get(eventType);
                if (!subscribers.contains(listener)) {
                    subscribers.add(listener);
                }
            } else {
                List<GameEventListener> subscribers = new ArrayList<>();
                subscribers.add(listener);
                subscriptions.put(eventType, subscribers);
            }
        }
    }

    private List<String> getEventNames(Class<? extends GameEvent>[] eventTypes) {
        List<String> eventNames = new ArrayList<String>();
        for(Class<? extends GameEvent> eventType : eventTypes) {
            eventNames.add(eventType.getSimpleName());
        }
        return eventNames;
    }

    @Override
    public void unsubscribe(GameEventListener listener, Class<? extends GameEvent>... eventTypes) {
        List<String> eventNames = getEventNames(eventTypes);

        log.log("Un-subscription: " + listener.getClass().getSimpleName() + " from " + eventNames);

        for(Class<? extends GameEvent> eventType : eventTypes) {
            if(subscriptions.containsKey(eventType)) {
                List<GameEventListener> subscribers = subscriptions.get(eventType);
                if (subscribers.contains(listener)) {
                    subscribers.remove(listener);
                }
            }
        }
    }

    @Override
    public void dropAllListenersForEvents(Class<? extends GameEvent>... eventTypes) {
        for(Class<? extends GameEvent> type : eventTypes) {

            log.log("Event: dropping all listeners for event '" + type + "'");
            subscriptions.remove(type);
        }
    }

    @Override
    public void dropAllListeners() {
        log.log("Event: dropping all listeners!");
        subscriptions.clear();
    }

    @Override
    public void publishEvent(GameEvent event) {
        log.log("Event publish: " + event.getClass().getSimpleName());

        Class<? extends GameEvent> eventType = event.getClass();
        if(subscriptions.containsKey(eventType)) {
            List<GameEventListener> subscribers = subscriptions.get(eventType);
            for(GameEventListener subscriber : subscribers) {
                log.log("Event sending: " + event.getClass().getSimpleName() + " to " + subscriber.getClass().getSimpleName());
                try {
                    subscriber.processEvent(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
