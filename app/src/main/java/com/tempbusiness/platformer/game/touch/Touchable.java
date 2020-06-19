package com.tempbusiness.platformer.game.touch;

import android.graphics.Rect;

import com.tempbusiness.platformer.game.graphics.Display;

public abstract class Touchable {
    private float x,y,w,h;
    private boolean touching = false;
    public abstract void downAction();
    public abstract void upAction();

    public Touchable(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public static Touchable basic(float x, float y, float w, float h) {
        return new Touchable(x,y,w,h) {
            @Override
            public void downAction() {

            }

            @Override
            public void upAction() {

            }
        };
    }

    public void down() {
        touching = true;
        downAction();
    }
    public void up() {
        touching = false;
        upAction();
    }

    public float x() {
        return x;
    }
    public float y() {
        return y;
    }
    public float w() {
        return w;
    }
    public float h() {
        return h;
    }

    public boolean isInTouch() {
        return touching;
    }

    public Rect bounds() {
        return Display.rect(x,y,w,h);
    }
}
