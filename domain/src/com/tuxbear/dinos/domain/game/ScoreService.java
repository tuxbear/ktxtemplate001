package com.tuxbear.dinos.domain.game;

public interface ScoreService {
    int getScore(MissionResult result);
    int getFirstMoverScore(Long millisBeforeMove);
    int moveScore(int moves);
}
