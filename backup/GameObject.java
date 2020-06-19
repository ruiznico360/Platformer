package com.tempbusiness.platformer.backup;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.tempbusiness.platformer.fileio.FileLoader.Image;

public class GameObject extends Box {
    public Platformer handler;
    public static final float indent = 1f/20f;
    public GameObject(float x, float y, float w, float h, int color, Platformer handler) {
        super(x,y,w,h,color);
        this.handler = handler;
    }
    public GameObject(float x, float y, float w, float h, Image img, Platformer handler) {
        super(x,y,w,h,img);
        this.handler = handler;
    }

    public void render(Renderer canvas) {
        renderGameObject(new Renderer.GRenderer(canvas.canvas, handler));
    }

    public void renderGameObject(Renderer.GRenderer canvas) {
        if (img == null) {
            canvas.drawRect(x,y,w,h, color);
        }else{
            canvas.drawBitmap(x,y,w,h, img);
        }
    }
    public Hitbox getHitbox() {
        return new Hitbox(x,y,w,h);
    }

    public static class Hitbox {
        public RectF full, l,r,t,b, cTL, cTR, cBL, cBR;


        public Hitbox(float x, float y, float w, float h) {
            final float overlap = indent;
            full = Display.genRectF(x,y,w,h);
            l = Display.genRectF(x, y + h * overlap, w * 0.5f, h * (1f- 2 * overlap));
            r = Display.genRectF(x + w * 0.5f, y + h * overlap, w * 0.5f, h * (1f- 2 * overlap));
            t = Display.genRectF(x + w * indent, y + h * (1f - indent), w * (1f - 2*overlap), h * indent);
            b = Display.genRectF(x + w * indent, y, w * (1f - 2*overlap), h * indent);

        }
        public Hitbox() {

        }
    }
    public void drawHitbox(Canvas canvas) {
        Hitbox h = getHitbox();
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        canvas.drawRect(h.t.left, h.t.top, h.t.right, h.t.bottom, p);
        p.setColor(Color.BLUE);
        canvas.drawRect(h.b.left, h.b.top, h.b.right, h.b.bottom, p);
        p.setColor(Color.RED);
        canvas.drawRect(h.l.left, h.l.top, h.l.right, h.l.bottom, p);
        p.setColor(Color.YELLOW);
        canvas.drawRect(h.r.left, h.r.top, h.r.right, h.r.bottom, p);
    }
}
