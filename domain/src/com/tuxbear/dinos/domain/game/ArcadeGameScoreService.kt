package com.tuxbear.dinos.domain.game

import java.lang.Integer.max

class ArcadeGameScoreService : ScoreService {
    override fun getFirstMoverScore(millisBeforeMove: Long?): Int {
        return if (millisBeforeMove == null) 0 else (5000 - (millisBeforeMove/10)).toInt()
    }

    override fun getMoveScore(moves: Int): Int {
        return max(5000 - (500 * moves), 0)
    }

    override fun getScore(result: MissionResult): Int {

        val firstMoveTimestamp = result.moveSequence.moves[0]?.timestamp
        val firstMoveScore = getFirstMoverScore(firstMoveTimestamp)
        val moveScore = getMoveScore(result.moveSequence.moves.size)

        return moveScore + firstMoveScore
    }
}