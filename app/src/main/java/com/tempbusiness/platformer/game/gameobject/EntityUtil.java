package com.tempbusiness.platformer.game.gameobject;

import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.util.Util;

public class EntityUtil {

    public static void applyGravity(Entity ev) {
        accelerateYTo(ev, -Entity.TERM_VEL, Entity.G);
    }

    public static void accelerateXTo(Entity ev, float vel, float acc) {
        int dir = (int) Math.signum(vel - ev.velX);
        float accDir = dir * acc;

        if (dir * ev.velX + accDir > dir * vel) {
            ev.accX = 0;
            ev.velX = vel;
        }else{
            ev.accX = accDir;
        }
    }
    public static void accelerateYTo(Entity ev, float vel, float acc) {
        int dir = (int) Math.signum(vel - ev.velY);
        float accDir = dir * acc;

        if (dir * ev.velY + accDir > dir * vel) {
            ev.accY = 0;
            ev.velY = vel;
        }else{
            ev.accY = accDir;
        }
    }
}
