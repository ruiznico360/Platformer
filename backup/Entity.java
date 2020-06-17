package com.tempbusiness.platformer.backup;

import java.util.ArrayList;

public abstract class Entity extends GameObject {
    public static final float MIN_VEL = 0.0001f,GRAVITY = -0.01f, TERM_VEL = -2;
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

        if (Math.abs(velX) < MIN_VEL) {
            velX = 0;
        }
        if (Math.abs(velY) < MIN_VEL) {
            velY = 0;
        }

        gameObjectCollision();
    }
    public void tick() {
        updateLocation();
    }

    public Hitbox gameObjectCollision() {
        float iterations = 1;
        boolean hl = false, hr = false, ht = false, hb = false;
        if (Math.max(Math.abs(velX), Math.abs(velY)) > indent) {
            iterations = Math.max(Math.abs(velX), Math.abs(velY)) / indent;
        }

        ArrayList<Block> blockList = new ArrayList<>();
        ArrayList<Entity> entityList = new ArrayList<>();
        int sX = (int)Math.floor(Math.min(x, x + velX)), sY = (int)Math.floor(Math.min(y, y + velY)), fX = (int)Math.ceil(Math.max(x, x + velX)), fY = (int)Math.ceil(Math.max(y, y + velY));

        for (int i = 0; i < handler.graphics.size(); i++) {
            if (handler.graphics.get(i) instanceof Block) {
                Block bl = ((Block) handler.graphics.get(i));

                if (bl.x >= sX && bl.x <= fX && bl.y >= sY && bl.y <= fY) {
                    blockList.add(bl);
                }
            }else if (handler.graphics.get(i) instanceof Entity) {
                Entity bl = ((Entity)handler.graphics.get(i));

                if (bl != this) {
                    entityList.add(bl);
                }
            }
        }

        float prevX = x, prevY = y;
        for (int n = 1; n <= Math.ceil(iterations); n++) {
            float tempX = prevX + (float)(velX / Math.ceil(iterations)), tempY = prevY + (float)(velY / Math.ceil(iterations));

            for (Block bl : blockList) {
                Hitbox h = new Hitbox(tempX, tempY, w, this.h);
                Hitbox b = bl.getHitbox();

                if (h.t.intersect(b.b)) {
                    velY = 0;
                    accY = 0;
                    tempY = (bl.y - 1.0f);
                    ht = true;
                }else if (h.b.intersect(b.t)) {
                    velY = 0;
                    accY = 0;
                    tempY = (bl.y + 1.0f);
                    hb = true;
                }
//            }
//            for (Block bl : blockList) {
//                Hitbox h = new Hitbox(tempX, tempY, w, this.h);
//                Hitbox b = bl.getHitbox();

                else if (h.l.intersect(b.r)) {
                    velX = 0;
                    accX = 0;
                    tempX = (bl.x + 1.0f);
                    hl = true;
                }else if (h.r.intersect(b.l)) {
                    velX = 0;
                    accX = 0;
                    tempX = (bl.x - 1.0f);
                    hr = true;
                }
            }
            Entity[] eList = new Entity[entityList.size()];
            entityList.toArray(eList);

            for (Entity e : eList) {
                Hitbox h = new Hitbox(tempX, tempY, w, this.h);
                Hitbox b = e.getHitbox();
                Hitbox interact = new Hitbox();
                if (h.t.intersect(b.b)) {
                    interact.b = b.b;
                    entityCollsion(interact,e);
                    entityList.remove(e);
//                    break;
                }else if (h.b.intersect(b.t)) {
                    interact.t = b.t;
                    entityCollsion(interact,e);
                    entityList.remove(e);
//                    break;
                }else if (h.l.intersect(b.r)) {
                    interact.r = b.r;
                    entityCollsion(interact,e);
                    entityList.remove(e);
//                    break;
                }else if (h.r.intersect(b.l)) {
                    interact.l = b.l;
                    entityCollsion(interact,e);
                    entityList.remove(e);
//                    break;
                }
            }

            prevX = tempX;
            prevY = tempY;
        }
        x = prevX;
        y = prevY;
        grounded = hb;

        Hitbox interacted = getHitbox();
        interacted.t = ht ? interacted.t : null;
        interacted.b = hb ? interacted.b : null;
        interacted.l = hl ? interacted.l : null;
        interacted.r = hr ? interacted.r : null;
        return interacted;
    }

    public void stop() {
        velX = 0;
        velY = 0;
        accX = 0;
        accY = 0;
    }
    public abstract void gravity();

    public void entityCollsion(Hitbox collision, Entity en) {

    }
}
