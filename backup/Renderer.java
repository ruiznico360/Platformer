package com.tempbusiness.platformer.backup;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Renderer {
    public Canvas canvas;

    public Renderer(Canvas canvas) {
        this.canvas = canvas;
    }

    public void drawRect(Rect r, int color) {
        if (oob(r)) return;
        canvas.drawRect(r, paint(color));
    }

    public void drawBitmap(Rect r, FileLoader.Image img) {
        if (oob(r)) return;
        canvas.drawBitmap(img.img, null, r, null);
    }

    public boolean oob(Rect r) {
        if (r.left >= Display.WIDTH || r.right <= 0 || r.top >=  Display.HEIGHT || r.bottom <= 0) return true;
        return false;
    }
    public Paint paint(int color) {
        Paint p = new Paint();
        p.setColor(color);
        return p;
    }
    public static class GRenderer extends Renderer {
        public Platformer handler;
        public GRenderer(Canvas canvas, Platformer handler) {
            super(canvas);
            this.handler = handler;
        }

        public void drawRect(float x, float y, float w, float h, int color) {
            Rect r = Display.genRect(x,y,w,h);
            r.left += handler.cam.x + Display.OFFSET_SCREEN_X;
            r.right += handler.cam.x + Display.OFFSET_SCREEN_X;
            r.top += handler.cam.y;
            r.bottom += handler.cam.y;

            drawRect(r,color);
        }

        public void drawBitmap(float x, float y, float w, float h, FileLoader.Image img) {
            Rect r = Display.genRect(x,y,w,h);
            r.left += handler.cam.x + Display.OFFSET_SCREEN_X;
            r.right += handler.cam.x + Display.OFFSET_SCREEN_X;
            r.top += handler.cam.y;
            r.bottom += handler.cam.y;

            drawBitmap(r, img);
        }
        public static int absoluteX(float gameX, Platformer handler) {
            return (int)(Display.displayX(gameX) + handler.cam.x + Display.OFFSET_SCREEN_X);
        }
        public static int absoluteY(float gameY, Platformer handler) {
            return (int)(Display.displayY(gameY) + handler.cam.y);
        }
    }
}
