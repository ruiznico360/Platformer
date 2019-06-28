package com.tempbusiness.platformer.graphicobjects;

import android.graphics.Color;

import com.tempbusiness.platformer.game.Platformer;

public class Player extends Entity {
    public static final float MAX_RUNNING_SPEED = 0.2f, FRICTION_RUNNING = 0.01f, RUN_ACC = 0.0025f, JUMP_SPEED = 0.2f, BOUNCE_ENEMY = 0.15f,CLIMB_SPEED_X = 0.04f,CLIMB_SPEED_Y = 0.08f,MAX_SWIM_SPEED = MAX_RUNNING_SPEED / 3.5f, SWIM_ACC = RUN_ACC * 0.75f, SWIM_FORCE = SWIM_ACC * 20, SWIM_ASCEND = 0.065f, MAX_SWIM_ASCEND = 0.1f,SWIM_GRAVITY = GRAVITY / 16, SWIM_TERM_VEL = TERM_VEL / 16;
    public static final int JUMP_FRAMES = 20;
    public static final String WALK = "WALK", CLIMB = "CLIMB", SWIM = "SWIM";
    public float inputAcc, inputVel;
    public int ascendCounter = JUMP_FRAMES;
    public String moveState = WALK;
    public Player(float x, float y, Platformer handler) {
        super(x,y,1,1, Color.rgb(255,100,0), handler);
    }

    public void stop() {
        super.stop();
        inputAcc = 0;
        inputVel = 0;
    }
    public void updateLocation() {
        if (moveState.equals(WALK)) {
            if (inputVel + inputAcc < -MAX_RUNNING_SPEED) inputVel = -MAX_RUNNING_SPEED;
            else if (inputVel + inputAcc > MAX_RUNNING_SPEED) inputVel = MAX_RUNNING_SPEED;
            else inputVel += inputAcc;

            if (inputAcc == 0 && inputVel != 0 && grounded) {
                if (Math.abs(inputVel) - FRICTION_RUNNING < 0) {
                    inputVel = 0;
                } else {
                    inputVel += inputVel > 0 ? -FRICTION_RUNNING : FRICTION_RUNNING;
                }

            }
            velX = inputVel;
        }else if (moveState.equals(SWIM)) {
            if (inputVel + inputAcc < -MAX_SWIM_SPEED) inputVel = -MAX_SWIM_SPEED;
            else if (inputVel + inputAcc > MAX_SWIM_SPEED) inputVel = MAX_SWIM_SPEED;
            else inputVel += inputAcc;

            if (velY + accY > MAX_SWIM_ASCEND) {
                velY = MAX_SWIM_ASCEND;
                accY = 0;
            }

            if (inputAcc == 0 && inputVel != 0) {
                if (Math.abs(inputVel) - FRICTION_RUNNING / 15 < 0) {
                    inputVel = 0;
                } else {
                    inputVel += inputVel > 0 ? -FRICTION_RUNNING / 15: FRICTION_RUNNING / 15;
                }

            }
            velX = inputVel;
        }

        velX += accX;
        velY += accY;

        if (Math.abs(velX) < MIN_VEL) {
            velX = 0;
        }
        if (Math.abs(velY) < MIN_VEL) {
            velY = 0;
        }

        Hitbox collision = gameObjectCollision();
        if (collision.l != null || collision.r != null) inputVel = 0;
        if (collision.t != null) ascendCounter = JUMP_FRAMES;

    }
    public void gravity() {
        if (moveState.equals(WALK)) {
            if (accY + velY > -2) {
                accY = GRAVITY;
            } else {
                accY = 0;
            }
        }else if (moveState.equals(SWIM)) {
            if (accY + velY > SWIM_TERM_VEL) {
                accY = SWIM_GRAVITY;
            } else {
                accY = (SWIM_TERM_VEL - velY) / 10;
            }
        }
    }

    public void entityCollsion(Hitbox collision, Entity en) {
        if (collision.t != null) {
            handler.graphics.remove(en);

            velY = BOUNCE_ENEMY;
            ascendCounter = 1;
        }else{
            handler.currentRoom.reset();
        }
    }
}
