package com.tempbusiness.platformer.game;

import android.graphics.Color;
import android.text.method.Touch;

import com.tempbusiness.platformer.background.Game;
import com.tempbusiness.platformer.background.GameHandler;
import com.tempbusiness.platformer.background.Touchable;
import com.tempbusiness.platformer.gameobject.Box;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.load.FileLoader;
import com.tempbusiness.platformer.util.Util;

import java.util.Random;

public class MainMenu extends GameHandler {
    public MainMenu(Game gameInstance) {
        super(gameInstance);
        gameObjects.add(new Box(0,0, Display.WIDTH, Display.HEIGHT, FileLoader.Image.BLOCK));

        Touchable touch = new Touchable(0,0,Display.WIDTH, Display.HEIGHT) {
            @Override
            public void downAction() {
                game.handler = new Platformer(game);
            }

            @Override
            public void upAction() {

            }
        };
        touchables.add(touch);
    }
    public void superTick() {

    }
}
