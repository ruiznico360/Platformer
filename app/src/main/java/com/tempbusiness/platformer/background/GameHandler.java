package com.tempbusiness.platformer.background;

import android.graphics.Canvas;

import com.tempbusiness.platformer.gameobject.GameObject;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;

public class GameHandler {
    public ArrayList<GameObject> gameObjects = new ArrayList<>();

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
