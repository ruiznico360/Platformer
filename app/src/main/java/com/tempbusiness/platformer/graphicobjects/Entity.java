package com.tempbusiness.platformer.graphicobjects;

import android.graphics.Canvas;
import android.os.SystemClock;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.load.FileLoader;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;

public abstract class Entity extends GameObject{
    public float velX, velY, accX, accY;
    public Entity(float x, float y, float w, float h, int color, Platformer handler) {
        super(x,y,w,h,color, handler);
    }
    public Entity(float x, float y, float w, float h, FileLoader.Image img, Platformer handler) {
        super(x,y,w,h,img, handler);
    }

    public void render(Canvas canvas) {
        super.render(canvas);
    }
    public void updateLocation() {
        velX += accX;
        velY += accY;

        blockCollision();
    }
    public void tick() {
        gravity();
        updateLocation();
    }

    public void blockCollision() {
        float iterations = 1;
        if (Math.max(Math.abs(velX), Math.abs(velY)) > indent) {
            iterations = Math.max(Math.abs(velX), Math.abs(velY)) / indent;
        }

        ArrayList<Block> blockList = new ArrayList<>();
        int sX = (int)Math.floor(Math.min(x, x + velX)), sY = (int)Math.floor(Math.min(y, y + velY)), fX = (int)Math.ceil(Math.max(x, x + velX)), fY = (int)Math.ceil(Math.max(y, y + velY));

        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (!(handler.gameObjects.get(i) instanceof Block)) continue;
            Block bl = ((Block)handler.gameObjects.get(i));

            if (bl.x >= sX && bl.x <= fX && bl.y >= sY && bl.y <= fY) {
                blockList.add(bl);
            }
        }

        float prevX = x, prevY = y;
        for (int n = 1; n <= Math.ceil(iterations); n++) {
            float tempX = prevX + (velX / iterations), tempY = prevY + (velY / iterations);

//            for (int i = 0; i < handler.gameObjects.size(); i++) {
//                if (!(handler.gameObjects.get(i) instanceof Block)) continue;
//                Block bl = ((Block)handler.gameObjects.get(i));

            for (Block bl : blockList) {
                Hitbox h = new Hitbox(tempX, tempY, w, this.h);
                Hitbox b = bl.getHitbox();

                if (h.t.intersect(b.b)) {
                    velY = 0;
                    accY = 0;
                    tempY = (bl.y - 1.0f);
                }else if (h.b.intersect(b.t)) {
                    velY = 0;
                    accY = 0;
                    tempY = (bl.y + 1.0f);
                }
            }
//            for (int i = 0; i < handler.gameObjects.size(); i++) {
//                if (!(handler.gameObjects.get(i) instanceof Block)) continue;
//                Block bl = ((Block)handler.gameObjects.get(i));
            for (Block bl : blockList) {
                Hitbox h = new Hitbox(tempX, tempY, w, this.h);
                Hitbox b = bl.getHitbox();

                if (h.l.intersect(b.r)) {
                    velX = 0;
                    accX = 0;
                    tempX = (bl.x + 1.0f);
                }else if (h.r.intersect(b.l)) {
                    velX = 0;
                    accX = 0;
                    tempX = (bl.x - 1.0f);
                }
            }

            prevX = tempX;
            prevY = tempY;
        }
        x = prevX;
        y = prevY;
    }

    public abstract void gravity();
}
