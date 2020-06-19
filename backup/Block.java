package com.tempbusiness.platformer.backup;

import android.graphics.Color;

import com.tempbusiness.platformer.backup.level.Room;
import com.tempbusiness.platformer.fileio.FileLoader.Image;

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
