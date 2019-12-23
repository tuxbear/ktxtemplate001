package com.tuxbear.dinos.domain.user

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson
import java.util.*

@DynamoDBTable(tableName = "playerTable")
public class Player {
    @DynamoDBHashKey
    var username: String? = null

    @DynamoDBAttribute
    var lastSeen: Date? = null

    @DynamoDBTypeConvertedJson
    @DynamoDBAttribute
    var friendIds: MutableSet<String> = HashSet()

    @DynamoDBTypeConvertedJson
    @DynamoDBAttribute
    var blocked: MutableSet<String> = HashSet()

    @DynamoDBTypeConvertedJson
    @DynamoDBAttribute
    var activeGames: MutableSet<String> = HashSet()

    init {
        lastSeen = Date()
    }
}