package com.tempbusiness.platformer.game;

import android.graphics.Color;

import com.tempbusiness.platformer.background.Game;
import com.tempbusiness.platformer.background.GameHandler;
import com.tempbusiness.platformer.background.Touchable;
import com.tempbusiness.platformer.gameobject.Box;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.load.FileLoader;

public class Platformer extends GameHandler {

    public Platformer(Game gameInstance) {
        super(gameInstance);

        gameObjects.add(new Box(0,0, Display.WIDTH, Display.HEIGHT, Color.YELLOW));
    }
    public void superTick() {

    }
}
