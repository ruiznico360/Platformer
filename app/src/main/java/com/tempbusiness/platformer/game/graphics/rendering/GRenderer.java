package com.tempbusiness.platformer.game.graphics.rendering;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.fileio.FileLoader;

public class GRenderer extends Renderer {
    public static int BLOCK_SIZE, G_WIDTH, G_HEIGHT;

    public void drawGRect(Platformer handler, float x, float y, float w, float h, int color) {
        Rect r = new Rect(displayX(x, handler), displayY(y + h, handler), displayX(x + w, handler), displayY(y, handler));
        drawRect(r,color);
    }

    public void drawGBitmap(Platformer handler, float x, float y, float w, float h, Bitmap img) {
        Rect r = new Rect(displayX(x, handler), displayY(y + h, handler), displayX(x + w, handler), displayY(y, handler));

        drawBitmap(r,img);
    }

    public static float preCamDisplayY(float y) {
        return (y * BLOCK_SIZE);
    }
    public static int displayY(float y, Platformer handler) {
        return (int)(Display.HEIGHT - (preCamDisplayY(y) + handler.getCam().y() + Display.OFFSET_SCREEN_Y));
    }

    public static float preCamDisplayX(float x) {
        return ((x * BLOCK_SIZE));
    }

    public static int displayX(float x, Platformer handler) {
        return (int)(preCamDisplayX(x) + handler.getCam().x() + Display.OFFSET_SCREEN_X);
    }
}
