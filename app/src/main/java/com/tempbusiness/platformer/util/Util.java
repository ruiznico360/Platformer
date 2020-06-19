package com.tempbusiness.platformer.util;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

public class Util {
    public static void log(Object... args) {
        String s = "";
        for (Object p : args) {
            s += p + " ";
        }
        Log.i("Platformer", s);
    }

    public static int randColor() {
        return Color.rgb(randInt(256), randInt(256), randInt(256));
    }
    public static int randInt(int max) {
        return new Random().nextInt(max);
    }
}
