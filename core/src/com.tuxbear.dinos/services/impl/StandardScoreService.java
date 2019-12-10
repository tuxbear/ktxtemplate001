package com.tuxbear.dinos.services.impl;

import com.tuxbear.dinos.services.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 02/01/14 Time: 11:33 To change this template use File | Settings | File
 * Templates.
 */
public class StandardScoreService implements ScoreService {

    private final int timeFactor = 4;

    @Override
    public int getScore(int movesMade, int timeConsumedInSeconds, int shortestPath) {
        if (movesMade == 0 || timeConsumedInSeconds == 0) return 0;

        return ((shortestPath * shortestPath) / movesMade) + (shortestPath * timeFactor / timeConsumedInSeconds);
    }
}
