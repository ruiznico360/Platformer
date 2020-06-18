package com.tempbusiness.platformer.game.level;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.graphicobjects.gameobject.Block;
import com.tempbusiness.platformer.graphicobjects.gameobject.Player;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.graphics.Renderer;

public class Camera {
    private final float Y_MAX_OFFSET, BOTTOM, X_MAX_OFFSET, BASE_SPEED, CENTER_X, CENTER_Y, LEVEL_SPEED;
    private Platformer handler;
    private int controlS;
    private float x,y, y_offset, x_offset;

    public Camera(Platformer handler, int controlS) {
        this.handler = handler;
        X_MAX_OFFSET = Display.G_WIDTH * 0.2f;
        Y_MAX_OFFSET = Display.G_HEIGHT * 0.1f;
        BASE_SPEED = Display.G_WIDTH / 200f;
        LEVEL_SPEED = BASE_SPEED * 2;
        CENTER_X = Display.G_WIDTH / 2;
        CENTER_Y = Display.G_HEIGHT / 2;
        x_offset = CENTER_X;
        y_offset = CENTER_Y;
        BOTTOM = (controlS + Display.BLOCK_SIZE);
    }

    public float x() {
        return x;
    }
    public float y() {
        return y;
    }
    public void tick() {
        handleX();
        handleY();

    }
    private void handleY() {
        if (handler.currentRoom.CAM_Y_BOUNDS.size() == 1) {
            handleUnboundedY();
        }else{
            handleLevelY();
        }
    }

    private void handleUnboundedY() {
        Room r = handler.currentRoom;
        float max_y = Renderer.preCamDisplayY(r.CAM_Y_BOUNDS.get(0).y);
        float py = Renderer.preCamDisplayY(handler.player.y);
        float ideal_y = - py;

        if (handler.player.velY != 0) {
            y_offset = handler.player.velY < 0 ? Math.max(y_offset - BASE_SPEED, CENTER_Y - Y_MAX_OFFSET) : Math.min(y_offset + BASE_SPEED, CENTER_Y + Y_MAX_OFFSET);
        }

        ideal_y += y_offset;

        if (ideal_y > BOTTOM) {
            y_offset = Math.max(y + py, CENTER_Y - Y_MAX_OFFSET);
            ideal_y = BOTTOM;
        }else if (ideal_y + max_y < Display.G_HEIGHT) {
            y_offset = Math.min(y + py, CENTER_Y + Y_MAX_OFFSET);
            ideal_y = Math.min(0, Display.G_HEIGHT - max_y);
        }

        y = ideal_y;
    }

    private void handleLevelY() {
        Room r = handler.currentRoom;
        Player p = handler.player;

        float scrollTo = BOTTOM;
        for (Block b : r.CAM_Y_BOUNDS) {
            if (p.y < b.y) break;
            scrollTo = BOTTOM - Renderer.preCamDisplayY(b.y);
        }

        if (y != scrollTo) {
            y = y > scrollTo ? Math.max(y - LEVEL_SPEED, scrollTo) : Math.min(y + LEVEL_SPEED, scrollTo);
        }

    }
    private void handleX() {
        Room r = handler.currentRoom;
        float max_x = Renderer.preCamDisplayX(r.CAM_MAX_X.x);

        float px = Renderer.preCamDisplayX(handler.player.x);
        float ideal_x = - px;

        if (handler.player.velX != 0) {
            x_offset = handler.player.velX > 0 ? Math.max(x_offset - BASE_SPEED, CENTER_X - X_MAX_OFFSET) : Math.min(x_offset + BASE_SPEED, CENTER_X + X_MAX_OFFSET);
        }

        ideal_x += x_offset;

        if (ideal_x > 0) {
            x_offset = Math.max(x + px, CENTER_X - X_MAX_OFFSET);
            ideal_x = 0;
        }else if (ideal_x + max_x < Display.G_WIDTH) {
            x_offset = Math.min(x + px, CENTER_X + X_MAX_OFFSET);
            ideal_x = Math.min(0, Display.G_WIDTH - max_x);
        }

        x = ideal_x;
    }
}
