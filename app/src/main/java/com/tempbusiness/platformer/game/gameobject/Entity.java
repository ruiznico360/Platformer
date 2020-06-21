package com.tempbusiness.platformer.game.gameobject;

import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.fileio.FileLoader;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity extends GameObject{
    protected static final float TERM_VEL = -1, G = -0.025f;
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
        Hitbox curr = getFutHitbox();
        Hitbox prev = getHitbox();
        int newYState = 0;

        List<Graphic> gameObjects = handler.getGameObjects();

        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i) instanceof Block) {
                Block b = (Block) gameObjects.get(i);
                Hitbox h = b.getHitbox();
                if (h.intersects(curr)) {
                    float[] interaction = b.interactEntity(prev, velX, velY);

                    x = interaction[0];
                    y = interaction[1];
                    velX = interaction[2];
                    velY = interaction[3];
                    newYState = Math.max((int)interaction[4], newYState);

                    if (velX == 0) accX = 0;
                    if (velY == 0) accY = 0;

                    curr = getFutHitbox();
                }
            }
        }
        this.yState = newYState;
    }

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

    private Hitbox getFutHitbox() {
        return new Hitbox(x + velX, y + velY, w, h);
    }

    protected void applyExternalForces() {}

}
