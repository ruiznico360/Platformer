package com.tempbusiness.platformer.graphicobjects.gameobject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.tempbusiness.platformer.graphics.Display;

public class Hitbox {
    protected float l,t,r,b;



    public Hitbox(float x, float y, float w, float h) {
        this.l = x;
        this.t = y + h;
        this.r = x + w;
        this.b = y;
    }

    public float x() {
        return l;
    }
    public float y() {
        return b;
    }

    public float w() {
        return r - l;
    }
    public float h() {
        return t - b;
    }

    public boolean intersects(Hitbox hit) {
        return alignedX(hit) && alignedY(hit);
    }

    public boolean alignedX(Hitbox hit) {
        return (hit.l < r && hit.r > l);
    }

    public boolean alignedY(Hitbox hit) {
        return (hit.b < t && hit.t > b);
    }

    public String toString() {
        return l + " " + t + " " + r + " " + b;
    }
}