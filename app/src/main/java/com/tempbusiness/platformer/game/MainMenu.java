package com.tempbusiness.platformer.game;

import com.tempbusiness.platformer.background.Game;
import com.tempbusiness.platformer.background.GameHandler;
import com.tempbusiness.platformer.background.Touchable;
import com.tempbusiness.platformer.graphicobjects.Box;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.load.FileLoader;

public class MainMenu extends GameHandler {
    public MainMenu() {
        graphics.add(new Box(0,0, Display.WIDTH, Display.HEIGHT, FileLoader.Image.BLOCK));

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
