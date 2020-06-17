package com.tempbusiness.platformer.graphicobjects.gameobject;

import android.graphics.Color;

import com.tempbusiness.platformer.game.Platformer;

public class Player extends Entity {
    public Player(float x, float y, Platformer handler) {
        super(x,y,0.9f,0.9f, Color.rgb(255,100,0), handler);
    }

    public void gravity() {

    }
}
