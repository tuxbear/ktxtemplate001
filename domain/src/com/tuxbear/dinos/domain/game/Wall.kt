package com.tuxbear.dinos.domain.game

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument

@DynamoDBDocument
class Wall {
    @DynamoDBAttribute
    var from: BoardPosition? = null

    @DynamoDBAttribute
    var to: BoardPosition? = null

    constructor(fromX:Int, fromY:Int, toX:Int, toY:Int)
            : this(BoardPosition(fromX, fromY), BoardPosition(toX, toY))

    constructor(from: BoardPosition, to: BoardPosition) {
        this.from = from
        this.to = to
    }

    constructor()
}