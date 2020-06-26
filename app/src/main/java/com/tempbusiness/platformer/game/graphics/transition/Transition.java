package com.tempbusiness.platformer.game.graphics.transition;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;
import com.tempbusiness.platformer.game.graphics.rendering.Renderer;
import com.tempbusiness.platformer.game.handler.Platformer;

public abstract class Transition {
    private Runnable end;
    private Platformer handler;
    private int delay = 0;
    protected int tickCount = 0;
    private boolean started = false;
    private Type type;
    public abstract void render(Renderer renderer);
    protected abstract int getDuration();

    public Transition (Platformer handler, Runnable end, Type t) {
        this.handler = handler;
        this.end = end;
        this.type = t;
    }

    public void setDelay(int ticks) {
        delay = ticks;
    }
    public void tick() {
        if (!started) return;

        if (tickCount == getDuration()) {
            handler.endTransition(end);
            return;
        }

        if (delay == 0) tickCount++;
        else delay--;
    }

    protected boolean inDelay() {
        return delay != 0;
    }

    public Type getType() {
        return type;
    }

    public void start() {
        started = true;
    }

    public enum Type {
        MARCH_FORWARD, MARCH_BACKWARD
    }
}