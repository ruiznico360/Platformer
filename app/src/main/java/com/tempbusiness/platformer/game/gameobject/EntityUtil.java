package com.tempbusiness.platformer.game.gameobject;

import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.util.Util;

public class EntityUtil {

    public static void applyGravity(Entity ev) {
        if (ev.velY + ev.accY < Entity.TERM_VEL) {
            ev.accY = 0;
            ev.velY = Entity.TERM_VEL;
        }else{
            ev.accY = Entity.G;
        }
    }

    public static void accelerateXTo(Entity ev, float speed, float acc) {
        int dir = (int) Math.signum(acc);

        if (dir * ev.velX + acc > dir * speed) {
            ev.accX = 0;
            ev.velX = speed;
        }else{
            ev.accX = acc;
        }
    }
}
