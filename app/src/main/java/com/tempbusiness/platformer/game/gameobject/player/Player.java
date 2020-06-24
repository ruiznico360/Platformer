package com.tempbusiness.platformer.game.gameobject.player;

import android.graphics.Color;

import com.tempbusiness.platformer.game.gameobject.Entity;
import com.tempbusiness.platformer.game.gameobject.EntityUtil;
import com.tempbusiness.platformer.game.gameobject.Hitbox;
import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;
import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.fileio.FileLoader;
import com.tempbusiness.platformer.game.level.Controller;
import com.tempbusiness.platformer.util.Util;

public class Player extends Entity {
    private boolean facingRight = false, turning = false;
    private int walkCounter = 0;

    public Player(float x, float y, Platformer handler) {
        super(x,y,0.65f,0.8f, 0, handler);
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
        FileLoader.Image img;
        final float xIndent = 1 - w, yIndent = 1 - h;
        final int walkInterval = Math.min(calcWalkInterval(speedX()),calcWalkInterval(PlayerWalkPhysics.RUN_SPEED) * 2);

        if (isGrounded()) {

            if (turning) {
                img = FileLoader.Image.PLAYER_TURN;
            }else if (velX != 0) {
                if (walkCounter >= walkInterval) walkCounter = 0;
                img = walkCounter < walkInterval / 2 ? FileLoader.Image.PLAYER_WALK : FileLoader.Image.PLAYER_STILL;
                walkCounter++;
            }else img = FileLoader.Image.PLAYER_STILL;
        }else{
            img = FileLoader.Image.PLAYER_JUMP_SLOW;
        }

        renderer.drawGBitmap(handler, x - xIndent / 2,y,w * (1 + xIndent),h * (1 + yIndent), facingRight ? img.img : img.inv);

//        Hitbox h = getBottomHitbox();
//        renderer.drawGRect(handler, h.x(), h.y(), h.w(), h.h(), Color.argb(175,255,255,0));
//
//        h = getHitbox();
//        renderer.drawGRect(handler, h.x(), h.y(), h.w(), h.h(), Color.argb(175,255,0,0));
    }

    protected float hitboxIndent() {
        return 0.1f;
    }

    private int calcWalkInterval(float speed) {
        return (int)((60 / speed / 100) * 3);
    }
}