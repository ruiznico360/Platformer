package com.tempbusiness.platformer.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.graphics.Display;

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
        appContext = act;
    }

    public static int randColor() {
        return Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
    }
}
