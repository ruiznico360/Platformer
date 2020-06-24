package com.tempbusiness.platformer.game.gameobject;

import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.fileio.FileLoader;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity extends GameObject{
    public static final float TERM_VEL = 1, G = 0.025f;
    public float velX, velY, accX, accY;
    private int yState;

    public Entity(float x, float y, float w, float h, int color, Platformer handler) {
        super(x,y,w,h,color, handler);
    }
    public Entity(float x, float y, float w, float h, FileLoader.Image img, Platformer handler) {
        super(x,y,w,h,img, handler);
    }

    protected void updateLocation() {
        velX += accX;
        velY += accY;

        blockCollision();

        x += velX;
        y += velY;
    }
    public void tick() {
        applyExternalForces();
        updateLocation();
    }
    private void blockCollision() {
        Hitbox curr = getTopFutHitbox();
        Hitbox currB = getBottomFutHitbox();
        int newYState = 0;

        List<Graphic> gameObjects = handler.getGameObjects();
        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i) instanceof Block) {
                Block b = (Block)gameObjects.get(i);
                Hitbox hb = b.getHitbox();
                boolean fullIntersect = hb.intersects(curr);

                if (hb.intersects(currB) && !fullIntersect) {
                    y = hb.t;
                    velY = 0;
                    accY = 0;
                    newYState = 1;
                }else if (hb.intersects(new Hitbox(x + velX,y + velY,w,h))) {
                    float[] interaction = b.interactEntity(this, getTopHitbox());
                    x = interaction[0];
                    y = interaction[1];
                    velX = interaction[2];
                    velY = interaction[3];
                    newYState = Math.max((int)interaction[4], newYState);

                    if (velX == 0) accX = 0;
                    if (velY == 0) accY = 0;
                }
            }
        }
        this.yState = newYState;
    }

//    private void blockCollision() {
//        Hitbox curr = getFutHitbox();
//        Hitbox currB = getBottomFutureHitbox();
//        int newYState = 0;
//        ArrayList<Block> bInteractions = new ArrayList<>();
//        ArrayList<Block> tInteractions = new ArrayList<>();
//
//        List<Graphic> gameObjects = handler.getGameObjects();
//        for (int i = 0; i < gameObjects.size(); i++) {
//            if (gameObjects.get(i) instanceof Block) {
//                Block b = (Block)gameObjects.get(i);
//                Hitbox h = b.getHitbox();
//                if (h.intersects(currB)) bInteractions.add(b);
//                else if (h.intersects(curr)) tInteractions.add(b);
//            }
//        }
//
//        for (Block b : tInteractions) {
//            if (h.intersects(currB) && !fullIntersect) {
//                y = h.t;
//                velY = 0;
//                accY = 0;
//                newYState = 1;
//            }else if (fullIntersect) {
//                float[] interaction = b.interactEntity(this, getHitbox());
//
//                x = interaction[0];
//                y = interaction[1];
//                velX = interaction[2];
//                velY = interaction[3];
//                newYState = Math.max((int)interaction[4], newYState);
//
//                if (velX == 0) accX = 0;
//                if (velY == 0) accY = 0;
//            }
//        }
//        this.yState = newYState;
//    }

    public boolean isGrounded() {
        return yState == 1;
    }

    public boolean isOnCeiling() {
        return yState == 2;
    }

    public float speedX() {
        return Math.abs(velX);
    }

    public float speedY() {
        return Math.abs(velY);
    }

    protected float hitboxIndent() {
        return 0.2f;
    }

    protected Hitbox getTopHitbox() {
        return new Hitbox(x,y + hitboxIndent(), w, h - hitboxIndent());
    }

    protected Hitbox getTopFutHitbox() {
        Hitbox h = getTopHitbox();
        h.l += velX;
        h.r += velX;
        h.t += velY;
        h.b += velY;

        return h;
    }

    protected Hitbox getBottomHitbox() {
        return new Hitbox(x + hitboxIndent(),y,w - 2 * hitboxIndent(), hitboxIndent());
    }

    protected Hitbox getBottomFutHitbox() {
        Hitbox h = getBottomHitbox();
        h.l += velX;
        h.r += velX;
        h.t += velY;
        h.b += velY;

        return h;
    }
    /*
        06-23 16:42:59.616 9488-9488/com.tempbusiness.platformer I/Platformer: 48.0 4.0 49.0 3.0 intersects with 48.305 4.7999997 49.005 3.9999995 | 48.3 4.825 49.0 4.0249996 speeds:(0.005 -0.025)
        06-23 16:42:59.616 9488-9488/com.tempbusiness.platformer I/Platformer:                        new values:  48.3 4.8 49.0 4.0 0.005 0.0
        06-23 16:42:59.616 9488-9488/com.tempbusiness.platformer I/Platformer: 49.0 5.0 50.0 4.0 intersects with 48.305 4.8 49.005 4.0 | 48.3 4.825 49.0 4.0249996 speeds:(0.005 0.0)
        06-23 16:42:59.616 9488-9488/com.tempbusiness.platformer I/Platformer:                        new values:  48.3 4.825 49.0 4.0249996 0.0 0.0
        */
    protected void applyExternalForces() {}

}
