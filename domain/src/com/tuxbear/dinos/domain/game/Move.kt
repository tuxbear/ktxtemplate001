package com.tuxbear.dinos.domain.game

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument

@DynamoDBDocument
class Move(
        /**
         * The timestamp in millisecs from round start this move was made
         */
        @DynamoDBAttribute
        var timestamp: Long,

        @DynamoDBAttribute
        var pieceNumber: Int,

        @DynamoDBAttribute
        var direction: Direction
)