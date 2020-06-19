package com.tempbusiness.platformer.backup;

import com.tempbusiness.platformer.fileio.FileLoader.Image;

public abstract class Graphic {
    public float x, y, w, h;
    public int color;
    public Image img;

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

    public void tick() {

    }
    public abstract void render(Renderer canvas);
}
