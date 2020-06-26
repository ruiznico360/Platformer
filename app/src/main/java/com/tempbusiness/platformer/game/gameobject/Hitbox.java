package com.tempbusiness.platformer.game.gameobject;

public class Hitbox {
    public float l,t,r,b;



    protected Hitbox(float x, float y, float w, float h) {
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

    public boolean contains(Hitbox hit) { return l <= hit.l && r >= hit.r&& b <= hit.b && t >= hit.t ; }

    public boolean alignedX(Hitbox hit) {
        return (hit.l < r && hit.r > l);
    }

    public boolean alignedY(Hitbox hit) {
        return (hit.b < t && hit.t > b);
    }

    public String toString() {
        return "Hitbox: " + l + " " + t + " " + r + " " + b;
    }
}