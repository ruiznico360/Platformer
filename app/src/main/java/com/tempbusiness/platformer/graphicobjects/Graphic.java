package com.tempbusiness.platformer.graphicobjects;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.tempbusiness.platformer.load.FileLoader.Image;

public abstract class Graphic {
    public float x, y, w, h;
    public Paint color;
    public Image img;

    public Graphic(float x, float y, float w, float h, int color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        Paint p = new Paint();
        p.setColor(color);

        this.color = p;
    }
    public Graphic(float x, float y, float w, float h, Image img) {
        this(x,y,w,h,0);
        this.img = img;
    }

    public void tick() {

    }
    public abstract void render(Canvas canvas);
}
