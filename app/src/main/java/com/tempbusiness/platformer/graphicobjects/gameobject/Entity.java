package com.tempbusiness.platformer.graphicobjects.gameobject;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.load.FileLoader;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;

public abstract class Entity extends GameObject{
    public static final float TERM_VEL = -2;
    public float velX, velY, accX, accY;
    public boolean grounded = false;

    public Entity(float x, float y, float w, float h, int color, Platformer handler) {
        super(x,y,w,h,color, handler);
    }
    public Entity(float x, float y, float w, float h, FileLoader.Image img, Platformer handler) {
        super(x,y,w,h,img, handler);
    }

    public void updateLocation() {
        velX += accX;
        velY += accY;

        blockCollision();

        x += velX;
        y += velY;
    }
    public void tick() {
        updateLocation();
    }

    public void blockCollision() {
        Hitbox curr = getFutHitbox();
        Hitbox prev = getHitbox();

        for (int i = 0; i < handler.graphics.size(); i++) {
            if (handler.graphics.get(i) instanceof Block) {
                Block b = (Block) handler.graphics.get(i);
                Hitbox h = b.getHitbox();
                if (h.intersects(curr)) {

                    float[] interaction = b.interactEntity(prev, velX, velY);

                    x = interaction[0];
                    y = interaction[1];
                    velX = interaction[2];
                    velY = interaction[3];

                    curr = getFutHitbox();
                }
            }
        }
    }

    private Hitbox getFutHitbox() {
        return new Hitbox(x + velX, y + velY, w, h);
    }

    public void stop() {
        velX = 0;
        velY = 0;
        accX = 0;
        accY = 0;
    }
}
