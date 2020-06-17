package com.tempbusiness.platformer.graphicobjects.gameobject;

import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.game.level.Room;
import com.tempbusiness.platformer.graphicobjects.gameobject.GameObject;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.load.FileLoader.Image;
import com.tempbusiness.platformer.util.Util;

import java.util.Random;

public class Block extends GameObject {
    public static final String BRICK = "BRICK", GRASS = "GRASS";

    public Block(float x, float y, String type, Platformer handler) {
        super(x,y,1,1, 0, handler);

        img = Image.BLOCK;

        if (type.equals(GRASS)) {
            int rand = new Random().nextInt(3);

            if (rand == 0) img = Image.GRASS1;
            if (rand == 1) img = Image.GRASS2;
            if (rand == 2) img = Image.GRASS3;

        }
    }

    public float[] interactEntity(Hitbox prev, float velX, float velY) {
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
