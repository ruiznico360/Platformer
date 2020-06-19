package com.tempbusiness.platformer.game.handler;

import android.graphics.Canvas;

import com.tempbusiness.platformer.game.controls.Touchable;
import com.tempbusiness.platformer.game.Game;
import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.game.graphics.rendering.Renderer;

import java.util.ArrayList;

public class GameHandler {
    public ArrayList<Graphic> graphics = new ArrayList<>();
    public ArrayList<Touchable> touchables = new ArrayList<>();
    public int ticksSinceStart;
    public Game game;
    public Renderer renderer;

    public GameHandler(Game game) {
        this.game = game;
    }

    public void tick() {
        superTick();
    }
    public void render(Canvas canvas) {
        Renderer r = renderWith(canvas);
        for (int i = 0; i < graphics.size(); i++) {
            graphics.get(i).render(r);
        }
    }
    public void superTick() {

    }
    public Renderer renderWith(Canvas canvas) {
        if (renderer == null) renderer = new Renderer();
        this.renderer.setCanvas(canvas);
        return renderer;
    }
}
