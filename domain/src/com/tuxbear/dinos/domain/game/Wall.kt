package com.tuxbear.dinos.domain.game

class Wall(val from: BoardPosition, val to: BoardPosition) {
    constructor(fromX:Int, fromY:Int, toX:Int, toY:Int)
            : this(BoardPosition(fromX, fromY), BoardPosition(toX, toY))
}