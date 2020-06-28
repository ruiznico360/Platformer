package com.tempbusiness.platformer.resources;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;

public class ImageLoader {
    private String name;
    private float scaleTo;
    private boolean genInv;

    private ImageLoader(String name, float scaleTo, boolean genInv) {
        this.name = name;
        this.scaleTo = scaleTo;
        this.genInv = genInv;
    }

    protected static ImageLoader g(String name) {
        return new ImageLoader(name, GRenderer.BLOCK_SIZE, false);
    }
    
    protected static ImageLoader gI(String name) {
        return new ImageLoader(name, GRenderer.BLOCK_SIZE, true);
    }

    protected static ImageLoader r(String name, float scaleTo) {
        return new ImageLoader(name, scaleTo, false);
    }
    protected static ImageLoader r(String name) {
        return new ImageLoader(name, -1, false);
    }

    protected Bitmap[] loadFromDrawable(Context c) {
        Bitmap[] bmaps = new Bitmap[2];
        bmaps[0] = Display.resizeToScaleH(BitmapFactory.decodeResource(c.getResources(), c.getResources().getIdentifier(name, "drawable", c.getPackageName())), scaleTo);

        if (genInv) bmaps[1] = Display.inverse(bmaps[0]);

        return bmaps;
    }

    protected String getName() {
        return name;
    }
}
