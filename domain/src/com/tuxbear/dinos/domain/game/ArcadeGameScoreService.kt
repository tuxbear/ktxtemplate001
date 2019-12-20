package com.tuxbear.dinos.domain.game

import java.lang.Integer.max

public class ArcadeGameScoreService : ScoreService {

    override fun getScore(result: MissionResult): Int {

        val moveScore = max(10000 - (1000 * result.moveSequence.moves.size), 0)

        return (moveScore)
    }
}