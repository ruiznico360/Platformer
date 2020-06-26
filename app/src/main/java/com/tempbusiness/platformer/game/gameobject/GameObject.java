package com.tempbusiness.platformer.game.gameobject;

import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.graphics.Box;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;
import com.tempbusiness.platformer.fileio.FileLoader.Image;

public class GameObject extends Box {
    protected Platformer handler;

    public GameObject(float x, float y, float w, float h, int color, Platformer handler) {
        super(x,y,w,h,color);
        this.handler = handler;
    }
    public GameObject(float x, float y, float w, float h, Image img, Platformer handler) {
        super(x,y,w,h,img);
        this.handler = handler;
    }

    public void gRender(GRenderer canvas) {
        if (img == null) {
            canvas.drawGRect(handler, x,y,w,h, color);
        }else{
            canvas.drawGBitmap(handler, x,y,w,h, img.img);
        }
    }

    public Hitbox getHitbox() {
        return new Hitbox(x,y,w,h);
    }

    public void tick() {}
}
