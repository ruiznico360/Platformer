package com.tempbusiness.platformer.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.util.Util;

public class Display {
    public static int WIDTH, HEIGHT, BLOCK_SIZE;

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

        int offsetScreenX = (int)((float)(Display.WIDTH % Platformer.BLOCKS_PER_SCREEN) / 2f);
        r.left = (int) displayX(x) + offsetScreenX;
        r.top = (int) displayY(y + h);
        r.right = (int) displayX((x + w)) + offsetScreenX;
        r.bottom = (int) displayY((y));
        return r;
    }
    public static RectF genRectF(float x, float y, float w, float h) {
        RectF r = new RectF();

        int offsetScreenX = (int)((float)(Display.WIDTH % Platformer.BLOCKS_PER_SCREEN) / 2f);
        r.left = displayX(x) + offsetScreenX;
        r.top = displayY(y + h);
        r.right = displayX((x + w)) + offsetScreenX;
        r.bottom = displayY((y));
        return r;
    }
}
