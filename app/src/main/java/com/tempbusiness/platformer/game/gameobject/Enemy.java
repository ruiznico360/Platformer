package com.tempbusiness.platformer.game.gameobject;

import android.graphics.Color;

import com.tempbusiness.platformer.game.handler.Platformer;

public class Enemy extends Entity {
    public static final float RUN_VEL =  0.025f;

    public Enemy(float x, float y, Platformer handler) {
        super(x,y,1,1, Color.rgb(0,100,0), handler);
    }

    protected void updateLocation() {
    }
}
