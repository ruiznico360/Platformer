package com.tempbusiness.platformer.game.gameobject.player;

import android.graphics.Color;

import com.tempbusiness.platformer.game.gameobject.EntityUtil;
import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;
import com.tempbusiness.platformer.util.Util;

public class PlayerWalkPhysics {
    public static final float JUMP_VEL = 0.31f, MAX_RUN_SPEED = 0.3f, RUN_SPEED = 0.2f, RUN_ACC = 0.0075f, AIR_ACC = 0.0075f, RUN_DEC = 1.5f;
    private final int MAX_JUMP_FRAMES = 15;
    private Player player;
    private float jumpPower;
    private int jumpCounter;
    private PMeter pMeter;

    public void setPlayer(Player player) {
        this.player = player;
        this.pMeter = new PMeter();
    }

    public void handleJump(boolean jumpButton, boolean grounded) {
        if (jumpButton) {
            if (player.isOnCeiling()) jumpCounter = MAX_JUMP_FRAMES;

            if (jumpCounter < MAX_JUMP_FRAMES) {
                if (jumpCounter == 0) {
                    jumpPower = JUMP_VEL * (1 + player.speedX() / 2);
                }

                player.velY = jumpPower;
                jumpCounter++;
            }
        }else{
            if (grounded) jumpCounter = 0;
            else jumpCounter = MAX_JUMP_FRAMES;
        }
    }

    public void handleWalk(boolean left, boolean right, boolean jump) {
        boolean grounded = player.isGrounded();
        boolean turning = false;

        float acc = grounded ? RUN_ACC : AIR_ACC;

        if (left) {
            if (player.velX > 0) turning = true;

            EntityUtil.accelerateXTo(player, -pMeter.currentMaxSpeed, acc);
            player.setFacingRight(false);
        }else if (right) {
            if (player.velX < 0) turning = true;

            EntityUtil.accelerateXTo(player, pMeter.currentMaxSpeed, acc);
            player.setFacingRight(true);
        }else if (grounded){
            EntityUtil.accelerateXTo(player, 0, RUN_ACC/RUN_DEC);
        }else{
            player.accX = 0;
        }

        handleJump(jump, grounded);
        player.setTurning(turning);

        if (grounded) pMeter.tick();
    }

    private class PMeter {
        private static final int MAX = 60;
        private int size;

        private float currentMaxSpeed = RUN_SPEED;

        private PMeter() {
            size = 0;
        }

        private void tick() {
            if (player.speedX() >= currentMaxSpeed && (player.speedX() != 0 && Math.signum(player.velX) != -Math.signum(player.accX))) incPMeter();
            else decPMeter();
        }

        private void decPMeter() {
            currentMaxSpeed = RUN_SPEED;
            size = Math.max(0, size - 1);
        }

        private void incPMeter() {
            if (size + 1 == MAX) {
                currentMaxSpeed = MAX_RUN_SPEED;
                player.velX = Math.signum(player.velX) * MAX_RUN_SPEED;
            }

            size = Math.min(MAX, size + 1);
        }

        private boolean isFull() {
            return size == MAX;
        }
    }
}
