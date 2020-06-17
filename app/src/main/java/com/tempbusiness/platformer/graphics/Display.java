package com.tempbusiness.platformer.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;

import com.tempbusiness.platformer.background.GameHandler;
import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.util.Util;

public class Display {
    public static int WIDTH, HEIGHT, BLOCK_SIZE, OFFSET_SCREEN_X, OFFSET_SCREEN_Y, G_WIDTH, G_HEIGHT;


    public static void initDimens(Context c) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        Display.WIDTH = metrics.widthPixels;
        Display.HEIGHT = metrics.heightPixels;

        float dW = Display.WIDTH;
        float dH = Display.HEIGHT;
        float blockX = Platformer.BLOCKS_PER_X;
        float blockY = Platformer.BLOCKS_PER_Y;

        if (dW / dH < blockX / blockY) {
            Display.BLOCK_SIZE = (int)(dW / blockX);
        }else{
            Display.BLOCK_SIZE = (int)(dH / blockY);
        }

        G_WIDTH = (int) blockX * Display.BLOCK_SIZE;
        G_HEIGHT = (int) blockY * Display.BLOCK_SIZE;
        Display.OFFSET_SCREEN_X = (int)((dW - G_WIDTH) / 2f);
        Display.OFFSET_SCREEN_Y = (int)((dH - G_HEIGHT) / 2f);

    }

    public static Rect rect(float x, float y, float w, float h) {
        Rect r = new Rect();
        r.left = (int) x;
        r.top = (int) y;
        r.right = (int) (x + w);
        r.bottom = (int) (y + h);
        return r;
    }

    public static RectF rectF(float x, float y, float w, float h) {
        RectF r = new RectF();
        r.left = (int) x;
        r.top = (int) y;
        r.right = (int) (x + w);
        r.bottom = (int) (y + h);
        return r;
    }
}
