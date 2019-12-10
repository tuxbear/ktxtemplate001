package com.tuxbear.dinos.domain.events;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 21/12/13 Time: 16:08 To change this template use File | Settings | File
 * Templates.
 */
public abstract class GameEvent {
    public String getSimpleEventName() {
        return this.getClass().getSimpleName();
    }


}
