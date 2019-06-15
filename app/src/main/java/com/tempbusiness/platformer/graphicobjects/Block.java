package com.tempbusiness.platformer.graphicobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.load.FileLoader.Image;

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

    public void render(Canvas canvas) {
        super.render(canvas);
    }
}
