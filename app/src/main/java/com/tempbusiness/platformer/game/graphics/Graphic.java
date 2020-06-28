package com.tempbusiness.platformer.game.graphics;

import com.tempbusiness.platformer.game.graphics.rendering.Renderer;
import com.tempbusiness.platformer.resources.Image;

public abstract class Graphic {
    public float x, y, w, h;
    protected int color;
    protected Image img;
    public abstract void render(Renderer canvas);

    public Graphic(float x, float y, float w, float h, int color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }
    public Graphic(float x, float y, float w, float h, Image img) {
        this(x,y,w,h,0);
        this.img = img;
    }

}
