package com.tempbusiness.platformer.gameobject;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.load.FileLoader.Image;
import com.tempbusiness.platformer.util.ImageUtil;

public class GameObject extends Box {

    public GameObject(float x, float y, float w, float h, int color) {
        super(x,y,w,h,color);
    }
    public GameObject(float x, float y, float w, float h, Image img) {
        super(x,y,w,h,img);
    }

    public void render(Canvas canvas) {
        Rect dst = Display.genRect(x,y,w,h);
        if (img == null) {
            canvas.drawRect(dst, color);
        }else{
            canvas.drawBitmap(img.img, null, dst, null);
        }
    }
}
