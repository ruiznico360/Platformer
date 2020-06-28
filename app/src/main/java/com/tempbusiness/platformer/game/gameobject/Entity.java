package com.tempbusiness.platformer.game.gameobject;

import com.tempbusiness.platformer.resources.Image;
import com.tempbusiness.platformer.game.gameobject.block.Block;
import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.game.handler.Platformer;

import java.util.List;

public abstract class Entity extends GameObject{
    public static final float TERM_VEL = 1, G = 0.025f;
    public float velX, velY, accX, accY;
    private int yState;

    public Entity(float x, float y, float w, float h, int color, Platformer handler) {
        super(x,y,w,h,color, handler);
    }
    public Entity(float x, float y, float w, float h, Image img, Platformer handler) {
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
        int newYState = 0;
        List<Graphic> gameObjects = handler.getGameObjects();

        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i) instanceof Block) {
                Block b = (Block)gameObjects.get(i);
                Hitbox hb = b.getHitbox();
                boolean fullIntersect = hb.intersects(getTopFutHitbox());

                if (hb.intersects(getBottomFutHitbox()) && !fullIntersect) {
                    y = hb.t;
                    velY = 0;
                    accY = 0;
                    newYState = 1;
                }else if (hb.intersects(getFutureHitbox())) {
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

    private Hitbox getFutureHitbox() {
        return new Hitbox(x + velX,y + velY,w,h);
    }

    private Hitbox getTopHitbox() {
        return new Hitbox(x,y + hitboxIndent(), w, h - hitboxIndent());
    }

    private Hitbox getTopFutHitbox() {
        Hitbox h = getTopHitbox();
        h.l += velX;
        h.r += velX;
        h.t += velY;
        h.b += velY;

        return h;
    }

    private Hitbox getBottomHitbox() {
        return new Hitbox(x + hitboxIndent(),y,w - 2 * hitboxIndent(), hitboxIndent());
    }

    private Hitbox getBottomFutHitbox() {
        Hitbox h = getBottomHitbox();
        h.l += velX;
        h.r += velX;
        h.t += velY;
        h.b += velY;

        return h;
    }

    protected void applyExternalForces() {}

}
