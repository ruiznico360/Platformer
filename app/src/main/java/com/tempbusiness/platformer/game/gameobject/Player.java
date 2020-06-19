package com.tempbusiness.platformer.game.gameobject;

import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.fileio.FileLoader;

public class Player extends Entity {
    public Player(float x, float y, Platformer handler) {
        super(x,y,0.8f,0.8f, FileLoader.Image.PLAYER, handler);
//        super(x,y,0.9f,0.9f, Color.rgb(255,100,0), handler);

    }
}
