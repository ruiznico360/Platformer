package com.tempbusiness.platformer.backup;

import android.graphics.Color;

public class Enemy extends Entity {
    public static final float RUN_VEL =  0.025f;

    public Enemy(float x, float y, Platformer handler) {
        super(x,y,1,1, Color.rgb(0,100,0), handler);
    }

    public void updateLocation() {
        velX += accX;
        velY += accY;

        if (Math.abs(velX) < MIN_VEL) {
            velX = 0;
        }
        if (Math.abs(velY) < MIN_VEL) {
            velY = 0;
        }

        ai(gameObjectCollision());
    }
    public void gravity() {
        if (accY + velY > -2) {
            accY = GRAVITY;
        }else {
            accY = 0;
        }
    }
    public void ai(Hitbox h) {
        if (h.l != null) velX = RUN_VEL;
        else if (h.r != null) velX = -RUN_VEL;
    }

    @Override
    public void entityCollsion(Hitbox collision, Entity en) {
        if (collision.l != null || collision.r != null) velX *= -1;
    }
}
