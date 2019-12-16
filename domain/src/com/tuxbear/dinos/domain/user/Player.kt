package com.tuxbear.dinos.domain.user

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.util.*

@DynamoDBTable(tableName = "playerTable")
class Player {
    @DynamoDBHashKey
    var username: String? = null

    @DynamoDBAttribute
    var lastSeen: Date? = null

    @DynamoDBAttribute
    var friendIds: MutableSet<String>? = null

    @DynamoDBAttribute
    var blocked: MutableSet<String>? = null

    @DynamoDBAttribute
    var activeGames: MutableSet<String>? = null

    init {
        lastSeen = Date()
    }
}