package com.tuxbear.dinos.domain.events;

import com.tuxbear.dinos.domain.game.MissionResult;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 23/12/13 Time: 14:17 To change this template use File | Settings | File
 * Templates.
 */
public class MissionAccomplishedEvent extends GameEvent {

    private MissionResult result;

    public MissionAccomplishedEvent(MissionResult result) {
        this.result = result;
    }

    public MissionResult getResult() {
        return result;
    }

}
