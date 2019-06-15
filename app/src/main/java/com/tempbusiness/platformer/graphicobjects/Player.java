package com.tempbusiness.platformer.graphicobjects;

import android.graphics.Canvas;
import android.graphics.Color;

import com.tempbusiness.platformer.game.Platformer;

public class Player extends Entity {
    public Player(float x, float y, Platformer handler) {
        super(x,y,1,1, Color.rgb(255,100,0), handler);
    }

    public void render(Canvas canvas) {
        super.render(canvas);
    }

    public void gravity() {
        if (accY + velY > -2) {
            accY = -0.005f;
        }else{
            accY = 0;
        }
    }
}
