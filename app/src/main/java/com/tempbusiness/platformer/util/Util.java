package com.tempbusiness.platformer.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.tempbusiness.platformer.graphics.Display;

public class Util {
    public static Context appContext;
    public static void log(String... args) {
        String s = "";
        for (String p : args) {
            s += p + " ";
        }
        Log.i("Platformer", s);
    }

    public static void init(Activity act) {
        DisplayMetrics metrics = act.getBaseContext().getResources().getDisplayMetrics();
        Display.WIDTH = metrics.widthPixels;
        Display.HEIGHT = metrics.heightPixels;
        Display.BLOCK_SIZE = Display.WIDTH / 30;
        appContext = act;
    }

}
