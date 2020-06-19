package com.tempbusiness.platformer.game.handler;

import com.tempbusiness.platformer.game.controls.Touchable;
import com.tempbusiness.platformer.game.Game;
import com.tempbusiness.platformer.game.graphics.Box;
import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.fileio.FileLoader;

public class MainMenu extends GameHandler {
    public MainMenu(Game gameInstance) {
        super(gameInstance);

        graphics.add(new Box(0,0, Display.WIDTH, Display.HEIGHT, FileLoader.Image.BLOCK));

        Touchable touch = new Touchable(0,0,Display.WIDTH, Display.HEIGHT) {
            @Override
            public void downAction() {
                game.setHandler(new Platformer(game));
            }

            @Override
            public void upAction() {

            }
        };
        touchables.add(touch);
    }
}
