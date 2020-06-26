package com.tempbusiness.platformer.game.gameobject.block;

import android.graphics.Color;

import com.tempbusiness.platformer.game.gameobject.Entity;
import com.tempbusiness.platformer.game.gameobject.GameObject;
import com.tempbusiness.platformer.game.gameobject.Hitbox;
import com.tempbusiness.platformer.game.gameobject.player.Player;
import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.level.Room;
import com.tempbusiness.platformer.fileio.FileLoader.Image;
import com.tempbusiness.platformer.util.Util;

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

    public float[] interactEntity(Entity en, Hitbox prev) {
        float[] interaction = new float[]{en.x, en.y, en.velX, en.velY, 0};
        Hitbox h = getHitbox();

        if (prev.alignedX(h)){
            if (prev.b < y) {
                interaction[4] = 2;
                interaction[1] = y - en.h;
            }else {
                interaction[4] = 1;
                interaction[1] = y + 1;
            }
            interaction[3] = 0;
        }else if (prev.alignedY(h)) {
            if (prev.l < x) {
                interaction[0] = x - en.w;
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

}
