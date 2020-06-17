package com.tempbusiness.platformer.graphicobjects.gameobject;

import android.graphics.Color;

import com.tempbusiness.platformer.game.Platformer;

public class Enemy extends Entity {
    public static final float RUN_VEL =  0.025f;

    public Enemy(float x, float y, Platformer handler) {
        super(x,y,1,1, Color.rgb(0,100,0), handler);
    }

    public void updateLocation() {
    }
    public void gravity() {
    }
    public void ai(Hitbox h) {

    }
}
