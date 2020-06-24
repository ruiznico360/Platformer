package com.tempbusiness.platformer.game.graphics.rendering;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.fileio.FileLoader;

public class Renderer {
    public Canvas canvas;

    public void setCanvas(Canvas c) {
        this.canvas = c;
    }

    public void drawRect(Rect r, int color) {
        if (oob(r)) return;
        canvas.drawRect(r, paint(color));
    }

    public void drawBitmap(Rect r, Bitmap img) {
        if (oob(r)) return;
        canvas.drawBitmap(img, null, r, null);
    }

    private boolean oob(Rect r) {
        if (r.left >= Display.WIDTH || r.right <= 0 || r.top >=  Display.HEIGHT || r.bottom <= 0) return true;
        return false;
    }
    public static Paint paint(int color) {
        Paint p = new Paint();
        p.setColor(color);
        return p;
    }
}
