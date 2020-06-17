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
