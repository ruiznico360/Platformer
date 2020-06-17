package com.tempbusiness.platformer.backup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tempbusiness.platformer.R;

public class FileLoader {

    public static void setup() {
        for (Image i : Image.values()) {
            if (i.name != null) i.img = BitmapFactory.decodeResource(Util.appContext.getResources(), Util.appContext.getResources().getIdentifier(i.name, "drawable", Util.appContext.getPackageName()));
            if (i.width != -1) i.img = ImageUtil.resizeToScale(i.img, Display.BLOCK_SIZE * i.width);
        }
    }
    public enum Image {
        BLOCK("block",1), GRASS1("grass1",1),GRASS2("grass2",1),GRASS3("grass3",1);

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
