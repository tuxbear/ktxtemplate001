package com.tuxbear.dinos.backend.dynamodb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.tuxbear.dinos.domain.game.MultiplayerGame
import java.util.*

class MultiplayerGameDao(db : AmazonDynamoDB) : DynamoDbDao<MultiplayerGame, String, Date>(MultiplayerGame::class.java, db) {

}