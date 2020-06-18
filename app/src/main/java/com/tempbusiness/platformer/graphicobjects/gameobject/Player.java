package com.tempbusiness.platformer.graphicobjects.gameobject;

import android.graphics.Color;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.graphics.Renderer;
import com.tempbusiness.platformer.load.FileLoader;
import com.tempbusiness.platformer.util.Util;

public class Player extends Entity {
    public Player(float x, float y, Platformer handler) {
        super(x,y,0.9f,0.9f, FileLoader.Image.PLAYER, handler);
    }

    public void gravity() {

    }
}
