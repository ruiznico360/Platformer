package com.tempbusiness.platformer.game.handler;

import android.content.Context;
import android.graphics.Canvas;

import com.tempbusiness.platformer.game.touch.Touchable;
import com.tempbusiness.platformer.game.Game;
import com.tempbusiness.platformer.game.graphics.rendering.GraphicsLayerer;
import com.tempbusiness.platformer.game.graphics.rendering.Renderer;
import com.tempbusiness.platformer.resources.Image;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;
import java.util.List;

public abstract class GameHandler {
    protected GraphicsLayerer graphics;
    private ArrayList<Touchable> touchables = new ArrayList<>();
    public int ticksSinceStart;
    protected Game game;
    protected Renderer renderer;
    protected abstract List<String> requiredResources();

    public GameHandler(Game game, int layers) {
        this.game = game;
        this.graphics = new GraphicsLayerer(layers);
    }

    public void tick() {
        superTick();
    }
    public void render(Canvas canvas) {
        Renderer r = renderWith(canvas);
        for (int i = 0; i < graphics.totalLayers(); i++) {
            for (int p = 0; p < graphics.layerSize(i); p++) {
                graphics.get(p,i).render(r);
            }
        }
    }

    protected Renderer renderWith(Canvas canvas) {
        if (renderer == null) renderer = new Renderer();
        this.renderer.setCanvas(canvas);
        return renderer;
    }

    public GraphicsLayerer getGraphics() {
        return graphics;
    }

    public ArrayList<Touchable> getTouchables() {
        return touchables;
    }

    protected final void loadResources() {
        List<String> res = requiredResources();
        for (Image i : Image.values()) {
            int index = res.indexOf(i.getName());

            if (index == -1) i.unload();
            else {
                Util.log(index);
                i.load(game.getContext());
                res.remove(index);
            }
        }
    }

    public boolean superTick() {return true;}
}
