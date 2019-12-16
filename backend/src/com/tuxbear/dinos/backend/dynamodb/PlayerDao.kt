package com.tuxbear.dinos.backend.dynamodb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.tuxbear.dinos.domain.user.Player
import java.util.*
import kotlin.collections.HashSet

class PlayerDao(db : AmazonDynamoDB) : DynamoDbDao<Player, String, Date>(Player::class.java, db) {

    fun addGameIdToPlayers(usernames : Set<String>, gameId : String) {
        usernames.parallelStream()
                .forEach { username ->
                    val player = getPlayerByUsername(username)
                    if (player?.activeGames == null) {
                        player?.activeGames = HashSet()
                    }

                    player?.activeGames.let { list ->  list?.add(gameId) }

                    tableMapper.save(player)
                }
    }

    fun getPlayerByUsername(username: String) : Player? {
        return tableMapper.load(username)
    }
}