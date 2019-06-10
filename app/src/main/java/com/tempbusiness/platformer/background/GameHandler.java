package com.tempbusiness.platformer.background;

import android.graphics.Canvas;

import com.tempbusiness.platformer.gameobject.Graphic;

import java.util.ArrayList;

public class GameHandler {
    public ArrayList<Graphic> gameObjects = new ArrayList<>();
    public ArrayList<Touchable> touchables = new ArrayList<>();
    public Game game;

    public GameHandler(Game game) {
        this.game = game;
    }
    public void tick() {
        superTick();
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).tick();
        }
    }
    public void render(Canvas canvas) {
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).render(canvas);
        }
    }
    public void superTick() {

    }
}
