package com.tempbusiness.platformer.util;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

import com.tempbusiness.platformer.graphics.Display;

public class ImageUtil {
    public static Bitmap resizeToScale(Bitmap original, int newWidth) {
        return Bitmap.createScaledBitmap(original, newWidth, (int)((float) original.getHeight() * ((float)(newWidth) / (float)original.getWidth())), false);
    }

    public static Rect bitmapBounds(Bitmap b) {
        return Display.rect(0,0,b.getWidth(),b.getHeight());
    }
    public static Paint color(int color) {
        Paint p = new Paint();
        p.setColor(color);
        return p;
    }
}
