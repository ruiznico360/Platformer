package com.tempbusiness.platformer.game.gameobject.player;

import android.graphics.Bitmap;

import com.tempbusiness.platformer.resources.Image;
import com.tempbusiness.platformer.game.gameobject.Entity;
import com.tempbusiness.platformer.game.gameobject.EntityUtil;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;
import com.tempbusiness.platformer.game.graphics.transition.Transition;
import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.level.Controller;

public class Player extends Entity {
    public static final float WIDTH = 0.65f, HEIGHT = 0.8f;
    private boolean facingRight = false, turning = false;
    private int walkCounter = 0;

    public Player(float x, float y, Platformer handler) {
        super(x,y,WIDTH,HEIGHT, 0, handler);
    }

    protected void applyExternalForces() {
        if (!Controller.DEBUG) EntityUtil.applyGravity(this);
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public void setTurning(boolean turning) {
        this.turning = turning;
    }

    public void gRender(GRenderer renderer) {
        final float xIndent = 1 - w, yIndent = 1 - h;
        Bitmap img;

        if (handler.getTransition() == null) {
            img = nonTransitionRender();
        }else{
            img = transitionRender();
        }
        renderer.drawGBitmap(handler, x - xIndent / 2,y,w * (1 + xIndent),h * (1 + yIndent), img);
    }

    private Bitmap transitionRender() {
        Bitmap img = null;

        if (handler.getTransition().getType() == Transition.Type.MARCH_FORWARD) {

            if (walkCounter >= minWalkInterval()) walkCounter = 0;
            img = walkCounter < minWalkInterval() / 2 ? Image.PLAYER_FORWARD.getImage() : Image.PLAYER_FORWARD.getInverseImage();

            walkCounter++;
        }else if (handler.getTransition().getType() == Transition.Type.MARCH_BACKWARD) {

            if (walkCounter >= minWalkInterval()) walkCounter = 0;
            img = walkCounter < minWalkInterval() / 2 ? Image.PLAYER_BACKWARD.getImage() : Image.PLAYER_BACKWARD.getInverseImage();

            walkCounter++;
        }

        return img;
    }

    private Bitmap nonTransitionRender() {
        Image img;

        final int walkInterval = Math.min(calcWalkInterval(speedX()), minWalkInterval());

        if (isGrounded()) {
            if (turning) {
                img = Image.PLAYER_TURN;
            }else if (velX != 0) {
                if (walkCounter >= walkInterval) walkCounter = 0;

                if (speedX() == PlayerWalkPhysics.MAX_RUN_SPEED) img = walkCounter < walkInterval / 2 ? Image.PLAYER_RUN_1 : Image.PLAYER_RUN_2;
                else img = walkCounter < walkInterval / 2 ? Image.PLAYER_WALK : Image.PLAYER_STILL;

                walkCounter++;
            }else img = Image.PLAYER_STILL;
        }else{
            if (speedX() == PlayerWalkPhysics.MAX_RUN_SPEED) img = Image.PLAYER_JUMP_FAST;
            else img = Image.PLAYER_JUMP_SLOW;
        }

        return facingRight ? img.getImage() : img.getInverseImage();
    }

    protected float hitboxIndent() {
        return 0.1f;
    }

    private int minWalkInterval() {
        return calcWalkInterval(PlayerWalkPhysics.RUN_SPEED) * 2;
    }
    private int calcWalkInterval(float speed) {
        return (int)((60 / speed / 100) * 3);
    }
}
