package com.tuxbear.dinos.domain.game

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum

@DynamoDBDocument
class Move {
        /**
         * The timestamp in millisecs from round start this move was made
         */
        @DynamoDBAttribute
        var timestamp: Long? = null

        @DynamoDBAttribute
        var pieceNumber: Int? = null

        @DynamoDBTypeConvertedEnum
        @DynamoDBAttribute
        var direction: Direction? = null

        constructor()

        constructor(timestamp: Long?, pieceNumber: Int?, direction: Direction?) {
                this.timestamp = timestamp
                this.pieceNumber = pieceNumber
                this.direction = direction
        }
}