package com.tempbusiness.platformer.backup;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageUtil {
    public static Bitmap resizeToScale(Bitmap original, int newWidth) {
        return Bitmap.createScaledBitmap(original, newWidth, (int)((float) original.getHeight() * ((float)(newWidth) / (float)original.getWidth())), false);
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
    public static Rect bitmapBounds(Bitmap b) {
        return rect(0,0,b.getWidth(),b.getHeight());
    }
    public static Paint color(int color) {
        Paint p = new Paint();
        p.setColor(color);
        return p;
    }
}
