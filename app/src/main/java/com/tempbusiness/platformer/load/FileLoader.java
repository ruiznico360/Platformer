package com.tempbusiness.platformer.load;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.tempbusiness.platformer.R;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.util.ImageUtil;
import com.tempbusiness.platformer.util.Util;

public class FileLoader {

    public static void setup() {
        for (Image i : Image.values()) {
            if (i.name != null) i.img = BitmapFactory.decodeResource(Util.appContext.getResources(), Util.appContext.getResources().getIdentifier(i.name, "drawable", Util.appContext.getPackageName()));
            if (i.width != -1) i.img = ImageUtil.resizeToScale(i.img, Display.BLOCK_SIZE * i.width);
        }
    }
    public enum Image {
        BLOCK("block",1);

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
}
