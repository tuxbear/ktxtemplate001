package com.tuxbear.dinos.domain.game

public class ArcadeGameScoreService : ScoreService {

    override fun getScore(result: MissionResult): Int {

        val timeToFirstMove = 10000 - result.moveSequence.moves[0].timestamp
        val moveScore = 10000 - (1000 * result.moveSequence.moves.size)

        return (timeToFirstMove + moveScore).toInt()
    }
}