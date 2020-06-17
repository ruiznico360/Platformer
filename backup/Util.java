package com.tempbusiness.platformer.backup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Random;

public class Util {
    public static Context appContext;
    public static void log(Object... args) {
        String s = "";
        for (Object p : args) {
            s += p + " ";
        }
        Log.i("Platformer", s);
    }

    public static void init(Activity act) {
        DisplayMetrics metrics = act.getBaseContext().getResources().getDisplayMetrics();
        Display.WIDTH = metrics.widthPixels;
        Display.HEIGHT = metrics.heightPixels;
        Display.BLOCK_SIZE = Display.WIDTH / Platformer.BLOCKS_PER_SCREEN;
        Display.OFFSET_SCREEN_X = (int)((float)(Display.WIDTH % Platformer.BLOCKS_PER_SCREEN) / 2f);
        appContext = act;
    }

    public static int randColor() {
        return Color.rgb(randInt(256), randInt(256), randInt(256));
    }
    public static int randInt(int max) {
        return new Random().nextInt(max);
    }
}
