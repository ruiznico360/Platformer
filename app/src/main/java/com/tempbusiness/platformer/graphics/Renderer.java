package com.tempbusiness.platformer.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.load.FileLoader;
import com.tempbusiness.platformer.util.Util;

public class Renderer {
    public Canvas canvas;

    public void setCanvas(Canvas c) {
        this.canvas = c;
    }

    public void drawRect(Rect r, int color) {
        if (oob(r)) return;
        canvas.drawRect(r, paint(color));
    }

    public void drawBitmap(Rect r, FileLoader.Image img) {
        if (oob(r)) return;
        canvas.drawBitmap(img.img, null, r, null);
    }

    protected boolean oob(Rect r) {
        if (r.left >= Display.WIDTH || r.right <= 0 || r.top >=  Display.HEIGHT || r.bottom <= 0) return true;
        return false;
    }
    protected Paint paint(int color) {
        Paint p = new Paint();
        p.setColor(color);
        return p;
    }

    public void drawGRect(Platformer handler, float x, float y, float w, float h, int color) {
        Rect r = new Rect(displayX(x, handler), displayY(y + h, handler), displayX(x + w, handler), displayY(y, handler));
        drawRect(r,color);
    }

    public void drawGBitmap(Platformer handler, float x, float y, float w, float h, FileLoader.Image img) {
        Rect r = new Rect(displayX(x, handler), displayY(y + h, handler), displayX(x + w, handler), displayY(y, handler));

        drawBitmap(r,img);
    }

    public static float preCamDisplayY(float y) {
        return (y * Display.BLOCK_SIZE);
    }
    public static int displayY(float y, Platformer handler) {
        return (int)(Display.HEIGHT - (preCamDisplayY(y) + handler.cam.y() + Display.OFFSET_SCREEN_Y));
    }

    public static float preCamDisplayX(float x) {
        return ((x * Display.BLOCK_SIZE));
    }

    public static int displayX(float x, Platformer handler) {
        return (int)(preCamDisplayX(x) + handler.cam.x() + Display.OFFSET_SCREEN_X);
    }

}
