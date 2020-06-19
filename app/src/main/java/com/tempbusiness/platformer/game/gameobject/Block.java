package com.tempbusiness.platformer.game.gameobject;

import android.graphics.Color;

import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.level.Room;
import com.tempbusiness.platformer.fileio.FileLoader.Image;

import java.util.Random;

public class Block extends GameObject {
    private Type type;

    public Block(float x, float y, Type type, Platformer handler) {
        super(x,y,1,1, 0, handler);
        this.type = type;

        if (type == Type.GRASS) {
            int rand = new Random().nextInt(3);

            if (rand == 0) img = Image.GRASS1;
            if (rand == 1) img = Image.GRASS2;
            if (rand == 2) img = Image.GRASS3;

        }else if (type == Type.DIRT) {
            color = Color.rgb(60,30,0);
        }else{
            img = Image.BLOCK;
        }
    }

    protected float[] interactEntity(Hitbox prev, float velX, float velY) {
        float[] interaction = new float[]{prev.x(), prev.y(), velX, velY};
        Hitbox h = getHitbox();

        if (prev.alignedX(h)){
            if (prev.b < y) {
                interaction[1] = y - prev.h();
            }else {
                interaction[1] = y + 1;
            }
            interaction[3] = 0;
        }else if (prev.alignedY(h)) {
            if (prev.l < x) {
                interaction[0] = x - prev.w();
            }else {
                interaction[0] = x + 1;
            }
            interaction[2] = 0;
        }
        return interaction;
    }

    public enum Type {
        BRICK, GRASS, DIRT
    }

    public static class Climbable extends GameObject {
        public Climbable(float x, float y, Platformer handler) {
            super(x,y,1,1, Color.GREEN, handler);
        }
    }

    public static class Water extends GameObject {
        public Water(float x, float y, Platformer handler) {
            super(x,y,1,1,Color.BLUE, handler);
        }
    }

    public static class Warpzone extends GameObject {
        public Room room;
        public Warpzone(float x, float y, Platformer handler, Room r) {
            super(x,y,1,1, Color.BLACK, handler);
            this.room = r;
        }
    }
}
