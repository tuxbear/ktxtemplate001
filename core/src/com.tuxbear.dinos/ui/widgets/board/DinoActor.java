package com.tuxbear.dinos.ui.widgets.board;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.tuxbear.dinos.domain.game.*;
import com.tuxbear.dinos.services.*;

/**
 * Created with IntelliJ IDEA. User: tuxbear Date: 03/12/13 Time: 17:10 To change this template use File | Settings | File
 * Templates.
 */
public class DinoActor extends BoardElement {
    Texture dinoTexture;

    final private int pieceNumber;
    private float size;

    private float speed = 0.1f;
    private Logger logger = IoC.resolve(Logger.class);

    private Direction directionFromPan;
    private Vector2 prevTouchDown;

    private DinoActor(final int pieceNumber, BoardPosition boardPosition, float size, final BoardWidget boardWidget) {
        super(boardWidget, boardPosition);
        this.pieceNumber = pieceNumber;
        this.size = size;
        dinoTexture = getDinoTextureFromNumber(pieceNumber);

        this.setSize(size, size);

        setX(getDrawX());
        setY(getDrawY());

        this.setBounds(getDrawX(), getDrawY(), size, size);
        this.setVisible(true);
        this.setTouchable(Touchable.enabled);

        this.addListener(new ActorGestureListener() {

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                super.pan(event, x, y, deltaX, deltaY);
                directionFromPan = flingDirectionFromPanData(deltaX, deltaY);
                logger.log("Pan: " + pieceNumber + " x: " + x + "y: " + y + "dx: " + deltaX + " dy: " + deltaY + "direction: "
                        + directionFromPan);

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Vector2 touchUpVector = localToStageCoordinates(new Vector2(x, y));
                logger.log("Touch up: " + pieceNumber + ": " + touchUpVector);


                Direction directionFromTouchUp = flingDirectionFromPanData(touchUpVector.x - prevTouchDown.x, touchUpVector.y - prevTouchDown.y);
                logger.log("Touch up delta: " + pieceNumber + ": " + (prevTouchDown.sub(touchUpVector)));

                if(directionFromTouchUp != null){
                    boardWidget.moveDino((DinoActor)event.getTarget(), directionFromTouchUp);
                    directionFromPan = null;
                }
            }

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                logger.log("Touch down: " + pieceNumber + " x: " + x + "y: " + y );
                logger.log("Touch down stage: " + pieceNumber + " x: " + localToStageCoordinates(new Vector2(x,y)));
                prevTouchDown = localToStageCoordinates(new Vector2(x,y));
            }
        });

        logPosition();

    }

    private void logPosition() {
        logger.log("DinoActor at " + getBoardPosition() + " - " + getX() + ", " + getY());
    }

    public static DinoActor createFromPieceNumber(int pieceNumber, float size, BoardWidget boardWidget) {
        return new DinoActor(pieceNumber, BoardPosition.OFF_BOARD, size, boardWidget);
    }

    public static Texture getDinoTextureFromNumber(int dinoNumber){
        return new Texture(Gdx.files.internal("dinos/" + dinoNumber + ".png"));
    }

    @Override
    public void draw(Batch batch, float alpha ){
        batch.draw(dinoTexture, getX(), getY(), size, size);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private Direction flingDirectionFromPanData(float deltaX, float deltaY) {
        int sensitivity = 20;
        if (Math.abs(deltaX) > sensitivity && Math.abs(deltaY) < sensitivity) {
            if (deltaX > 0) {
                return Direction.Right;
            } else {
                return Direction.Left;
            }

        } else if (Math.abs(deltaY) > sensitivity && Math.abs(deltaX) < sensitivity) {
            if(deltaY < 0) {
                return Direction.Down;
            } else {
                return Direction.Up;
            }
        }

        return null;
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getPieceNumber() {
        return pieceNumber;
    }
}
