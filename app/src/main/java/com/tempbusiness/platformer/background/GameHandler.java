package com.tempbusiness.platformer.background;

import android.graphics.Canvas;

import com.tempbusiness.platformer.graphicobjects.Graphic;
import com.tempbusiness.platformer.graphics.Renderer;

import java.util.ArrayList;

public class GameHandler {
    public ArrayList<Graphic> graphics = new ArrayList<>();
    public ArrayList<Touchable> touchables = new ArrayList<>();
    public int ticksSinceStart;
    public Game game;
    public AudioPlayer audioPlayer;

    public void tick() {
        superTick();
        for (int i = 0; i < graphics.size(); i++) {
            graphics.get(i).tick();
        }
    }
    public void render(Renderer canvas) {
        for (int i = 0; i < graphics.size(); i++) {
            graphics.get(i).render(canvas);
        }
    }
    public void superTick() {

    }
}
