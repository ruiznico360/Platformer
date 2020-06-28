package com.tempbusiness.platformer.resources;

import android.content.Context;
import android.graphics.Bitmap;

public enum Image {
    BACKGROUND(ImageLoader.r("background")),
    BLOCK(ImageLoader.g("block")),
    GRASS1(ImageLoader.g("grass1")),
    GRASS2(ImageLoader.g("grass2")),
    GRASS3(ImageLoader.g("grass3")),
    PLAYER(ImageLoader.g("player")),
    PLAYER_WALK(ImageLoader.gI("player_walk")),
    PLAYER_TURN(ImageLoader.gI("player_turn")),
    PLAYER_JUMP_SLOW(ImageLoader.gI("player_jump_slow")),
    PLAYER_JUMP_FAST(ImageLoader.gI("player_jump_fast")),
    PLAYER_RUN_1(ImageLoader.gI("player_run_1")),
    PLAYER_RUN_2(ImageLoader.gI("player_run_2")),
    PLAYER_STILL(ImageLoader.gI("player_still")),
    PLAYER_FORWARD(ImageLoader.gI("player_forward")),
    PLAYER_BACKWARD(ImageLoader.gI("player_backward"));

    private ImageLoader loader;
    private Bitmap bitmap, inverse;


    Image(ImageLoader i) {
        loader = i;
    }

    public void load(Context c) {
        if (loaded()) return;

        Bitmap[] b = loader.loadFromDrawable(c);
        bitmap = b[0];
        inverse = b[1];
    }

    public boolean loaded() {
        return bitmap != null;
    }

    public void unload() {
        bitmap = null;
        inverse = null;
    }

    public String getName() {
        return loader.getName().toUpperCase();
    }

    public Bitmap getImage() {
        return bitmap;
    }

    public Bitmap getInverseImage() {
        return inverse;
    }

}
