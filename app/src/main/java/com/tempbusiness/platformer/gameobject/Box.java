package com.tempbusiness.platformer.gameobject;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.load.FileLoader.Image;
import com.tempbusiness.platformer.util.ImageUtil;
import com.tempbusiness.platformer.util.Util;

public class Box extends GameObject {

    public Box(float x, float y, float w, float h, int color) {
        super(x,y,w,h,color);
    }
    public Box(float x, float y, float w, float h, Image img) {
        super(x,y,w,h,img);
    }

    public void render(Canvas canvas) {
        Rect dst = ImageUtil.rect(x,y,w,h);
        if (img == null) {
            canvas.drawRect(dst, color);
        }else{
            canvas.drawBitmap(img.img, null, dst, null);
        }
    }
}
