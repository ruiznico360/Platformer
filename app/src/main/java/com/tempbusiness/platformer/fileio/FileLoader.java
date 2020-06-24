package com.tempbusiness.platformer.fileio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tempbusiness.platformer.R;
import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;

public class FileLoader {

    public static void setup(Context c) {
        for (Image i : Image.values()) {
            if (i.name != null) i.img = BitmapFactory.decodeResource(c.getResources(), c.getResources().getIdentifier(i.name, "drawable", c.getPackageName()));
            if (i.gObject) i.img = Display.resizeToScale(i.img, GRenderer.BLOCK_SIZE);
            if (i.genInv) i.inv = Display.inverse(i.img);
        }
    }
    public enum Image {
        BLOCK("block"), GRASS1("grass1"),GRASS2("grass2"),GRASS3("grass3"),
        PLAYER("player"), PLAYER_WALK("player_walk", true), PLAYER_TURN("player_turn",true),
        PLAYER_JUMP_SLOW("player_jump_slow", true), PLAYER_JUMP_FAST("player_jump_fast",true),
        PLAYER_RUN("player_run",true), PLAYER_STILL("player_still",true)
        ;

        public String name;
        public Bitmap img, inv;
        public boolean genInv = false;
        public boolean gObject = true;
        public int orienation = 1;

        Image(String name) {
           this.name = name;
        }

        Image(String name, boolean genInv) {
            this.name = name;
            this.genInv = genInv;
        }

        Image(String name, boolean genInv, boolean gObject) {
            this.name = name;
            this.genInv = genInv;
            this.gObject = gObject;
        }

    }
    public enum Sound {
        JUMP(R.raw.jump), OVERWORLD(R.raw.overworld), TITLE(R.raw.title);

        public int name;

        Sound(int name) {
            this.name = name;
        }

    }
}
