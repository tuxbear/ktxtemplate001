package com.tuxbear.dinos.services;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 02/01/14 Time: 11:32 To change this template use File | Settings | File
 * Templates.
 */
public interface ScoreService {
    int getScore(int movesMade, int timeConsumedInSeconds, int shortestPath);
}
