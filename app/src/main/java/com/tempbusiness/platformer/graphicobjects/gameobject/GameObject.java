package com.tempbusiness.platformer.graphicobjects.gameobject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.graphicobjects.Box;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.graphics.Renderer;
import com.tempbusiness.platformer.load.FileLoader.Image;
import com.tempbusiness.platformer.util.ImageUtil;

public class GameObject extends Box {
    public Platformer handler;
    public GameObject(float x, float y, float w, float h, int color, Platformer handler) {
        super(x,y,w,h,color);
        this.handler = handler;
    }
    public GameObject(float x, float y, float w, float h, Image img, Platformer handler) {
        super(x,y,w,h,img);
        this.handler = handler;
    }

    public void render(Renderer canvas) {
        if (img == null) {
            canvas.drawGRect(handler, x,y,w,h, color);
        }else{
            canvas.drawGBitmap(handler, x,y,w,h, img);
        }
    }

    public Hitbox getHitbox() {
        return new Hitbox(x,y,w,h);
    }
}
