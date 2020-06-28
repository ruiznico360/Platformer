package com.tempbusiness.platformer.game.gameobject.block;

import android.graphics.Color;

import com.tempbusiness.platformer.resources.Image;
import com.tempbusiness.platformer.game.gameobject.GameObject;
import com.tempbusiness.platformer.game.gameobject.player.Player;
import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.level.Room;

public abstract class Warpzone extends GameObject {
    private Room room;

    public Warpzone(float x, float y, Platformer handler, Room r, Image img) {
        super(x,y,1,1, img, handler);
        this.room = r;
    }
    public Warpzone(float x, float y, Platformer handler, Room r, int color) {
        super(x,y,1,1, color, handler);
        this.room = r;
    }

    public void use(Player p) {
        if (p.isGrounded()) {
            handler.changeRoom(room);
        }
    }

    public boolean usable(Player p) {
        return getHitbox().contains(p.getHitbox()) && room != null;
    }

    public static class Door extends Warpzone {
        public Door(float x, float y, Platformer handler, Room r) {
            super(x,y,handler, r, Color.BLACK);
        }
    }
}
