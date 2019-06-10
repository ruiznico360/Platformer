package com.tempbusiness.platformer.game;

import android.graphics.Color;

import com.tempbusiness.platformer.background.Game;
import com.tempbusiness.platformer.background.GameHandler;
import com.tempbusiness.platformer.gameobject.Box;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.load.FileLoader;

import java.util.Random;

public class MainMenu extends GameHandler {
    public Game game;

    public MainMenu(Game game) {
        this.game = game;

        // no u
        gameObjects.add(new Box(10,100, Display.WIDTH / 2, Display.HEIGHT / 2, Color.YELLOW));
        gameObjects.add(new Box(Display.WIDTH / 2 + 10,Display.HEIGHT / 2 + 100, Display.WIDTH / 2, Display.HEIGHT / 2, FileLoader.Image.BLOCK));

    }
}
