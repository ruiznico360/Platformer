package com.tempbusiness.platformer.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.util.Util;

public class Display {
    public static int WIDTH, HEIGHT, BLOCK_SIZE, OFFSET_SCREEN_X;


    public static float displayY(float y) {
        return HEIGHT - (y * BLOCK_SIZE);
    }

    public static float displayX(float x) {
        return x * BLOCK_SIZE;
    }

    public static float displaySize(float size) {
        return size * BLOCK_SIZE;
    }

    public static Rect genRect(float x, float y, float w, float h) {
        Rect r = new Rect();

        r.left = (int) displayX(x);
        r.top = (int) displayY(y + h);
        r.right = (int) displayX((x + w));
        r.bottom = (int) displayY((y));
        return r;
    }
    public static RectF genRectF(float x, float y, float w, float h) {
        RectF r = new RectF();

        r.left = displayX(x);
        r.top = displayY(y + h);
        r.right = displayX((x + w));
        r.bottom = displayY((y));
        return r;
    }
}
