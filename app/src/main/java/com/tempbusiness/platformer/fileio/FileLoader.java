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
            if (i.width != -1) i.img = Display.resizeToScale(i.img, GRenderer.BLOCK_SIZE * i.width);
        }
    }
    public enum Image {
        BLOCK("block",1), GRASS1("grass1",1),GRASS2("grass2",1),GRASS3("grass3",1), PLAYER("player", 1);

        public String name;
        public Bitmap img;
        public int width = -1;

        Image(String name) {
           this.name = name;
        }

        Image(String name, int width) {
            this.name = name;
            this.width = width;
        }

        Image() {}
    }
    public enum Sound {
        JUMP(R.raw.jump), OVERWORLD(R.raw.overworld), TITLE(R.raw.title);

        public int name;

        Sound(int name) {
            this.name = name;
        }

    }
}
