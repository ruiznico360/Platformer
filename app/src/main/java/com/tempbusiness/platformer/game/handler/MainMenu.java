package com.tempbusiness.platformer.game.handler;

import com.tempbusiness.platformer.resources.Image;
import com.tempbusiness.platformer.game.touch.Touchable;
import com.tempbusiness.platformer.game.Game;
import com.tempbusiness.platformer.game.graphics.Box;
import com.tempbusiness.platformer.game.graphics.Display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenu extends GameHandler {
    public MainMenu(Game gameInstance) {
        super(gameInstance, 1);
        loadResources();

        graphics.add(new Box(0,0, Display.WIDTH, Display.HEIGHT, Image.BLOCK));

        Touchable touch = new Touchable(0,0,Display.WIDTH, Display.HEIGHT) {
            @Override
            public void downAction() {
                game.setHandler(new Platformer(game));
            }

            @Override
            public void upAction() {

            }
        };
        getTouchables().add(touch);
    }

    protected List<String> requiredResources() {
        List<String> images = new ArrayList<>();
        images.add(Image.BLOCK.getName());
        return images;
    }
}
