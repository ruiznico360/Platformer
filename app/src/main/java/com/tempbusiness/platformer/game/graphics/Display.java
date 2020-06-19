package com.tempbusiness.platformer.game.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;
import com.tempbusiness.platformer.game.handler.Platformer;

public class Display {
    public static int WIDTH;
    public static int HEIGHT;
    public static int OFFSET_SCREEN_X;
    public static int OFFSET_SCREEN_Y;


    public static void initDimens(Context c) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        Display.WIDTH = metrics.widthPixels;
        Display.HEIGHT = metrics.heightPixels;

        float dW = Display.WIDTH;
        float dH = Display.HEIGHT;
        float blockX = Platformer.BLOCKS_PER_X;
        float blockY = Platformer.BLOCKS_PER_Y;

        if (dW / dH < blockX / blockY) {
            GRenderer.BLOCK_SIZE = (int)(dW / blockX);
        }else{
            GRenderer.BLOCK_SIZE = (int)(dH / blockY);
        }

        GRenderer.G_WIDTH = (int) blockX * GRenderer.BLOCK_SIZE;
        GRenderer.G_HEIGHT = (int) blockY * GRenderer.BLOCK_SIZE;
        Display.OFFSET_SCREEN_X = (int)((dW - GRenderer.G_WIDTH) / 2f);
        Display.OFFSET_SCREEN_Y = (int)((dH - GRenderer.G_HEIGHT) / 2f);

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

    public static float dpToPx(float dp, Context c) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static Bitmap resizeToScale(Bitmap original, int newWidth) {
        return Bitmap.createScaledBitmap(original, newWidth, (int)((float) original.getHeight() * ((float)(newWidth) / (float)original.getWidth())), false);
    }
}
