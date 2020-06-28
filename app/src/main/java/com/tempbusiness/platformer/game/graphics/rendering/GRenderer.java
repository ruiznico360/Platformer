package com.tempbusiness.platformer.game.graphics.rendering;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.graphics.Display;

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

    public static int ABS_RIGHT() {
        return Display.OFFSET_SCREEN_X + G_WIDTH;
    }

    public static int ABS_BOTTOM() {
        return Display.OFFSET_SCREEN_Y + G_HEIGHT;
    }

    public static float farthestEdgeDist(float x, float y) {
        return Math.max(Math.max(Math.abs(ABS_BOTTOM() - y), Math.abs(y - Display.OFFSET_SCREEN_Y)), Math.max(Math.abs(ABS_RIGHT() - x), Math.abs(x - Display.OFFSET_SCREEN_X)));
    }
}
