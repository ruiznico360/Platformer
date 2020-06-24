package com.tempbusiness.platformer.game.graphics;

import android.graphics.Rect;

import com.tempbusiness.platformer.game.graphics.rendering.Renderer;
import com.tempbusiness.platformer.fileio.FileLoader.Image;

public class Box extends Graphic {

    public Box(float x, float y, float w, float h, int color) {
        super(x,y,w,h,color);
    }
    public Box(float x, float y, float w, float h, Image img) {
        super(x,y,w,h,img);
    }

    public void render(Renderer canvas) {
        Rect dst = Display.rect(x,y,w,h);
        if (img == null) {
            canvas.drawRect(dst, color);
        }else{
            canvas.drawBitmap(dst, img.img);
        }
    }
}
