package com.tempbusiness.platformer.background;

public abstract class Touchable {
    public float x,y,w,h;
    public boolean touching = false;

    public Touchable(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    public void down() {
        touching = true;
        downAction();
    }
    public void up() {
        touching = false;
        upAction();
    }
    public abstract void downAction();
    public abstract void upAction();
}
