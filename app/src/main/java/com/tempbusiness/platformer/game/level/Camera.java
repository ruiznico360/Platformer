package com.tempbusiness.platformer.game.level;

import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.gameobject.Block;
import com.tempbusiness.platformer.game.gameobject.Player;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;

public class Camera {
    private final float Y_MAX_OFFSET, X_MAX_OFFSET, BASE_SPEED, CENTER_X, CENTER_Y, LEVEL_SPEED;
    private Platformer handler;
    private int controlS;
    private float x,y, y_offset, x_offset;

    public Camera(Platformer handler, int controlS) {
        this.handler = handler;
        X_MAX_OFFSET = GRenderer.G_WIDTH * 0.2f;
        Y_MAX_OFFSET = GRenderer.G_HEIGHT * 0.1f;
        BASE_SPEED = GRenderer.G_WIDTH / 200f;
        LEVEL_SPEED = BASE_SPEED * 2;
        CENTER_X = GRenderer.G_WIDTH / 2;
        CENTER_Y = GRenderer.G_HEIGHT / 2;
        x_offset = CENTER_X;
        y_offset = CENTER_Y;
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
        float max_y = GRenderer.preCamDisplayY(r.CAM_Y_BOUNDS.get(0).y);
        float py = GRenderer.preCamDisplayY(handler.player.y);
        float ideal_y = - py;

        if (handler.player.velY != 0) {
            y_offset = handler.player.velY < 0 ? Math.max(y_offset - BASE_SPEED, CENTER_Y - Y_MAX_OFFSET) : Math.min(y_offset + BASE_SPEED, CENTER_Y + Y_MAX_OFFSET);
        }

        ideal_y += y_offset;

        if (ideal_y > 0) {
            y_offset = Math.max(y + py, CENTER_Y - Y_MAX_OFFSET);
            ideal_y = 0;
        }else if (ideal_y + max_y < GRenderer.G_HEIGHT) {
            y_offset = Math.min(y + py, CENTER_Y + Y_MAX_OFFSET);
            ideal_y = Math.min(0, GRenderer.G_HEIGHT - max_y);
        }

        y = ideal_y;
    }

    private void handleLevelY() {
        Room r = handler.currentRoom;
        Player p = handler.player;

        float scrollTo = 0;
        for (Block b : r.CAM_Y_BOUNDS) {
            if (p.y < b.y) break;
            scrollTo = -GRenderer.preCamDisplayY(b.y);
        }

        if (y != scrollTo) {
            y = y > scrollTo ? Math.max(y - LEVEL_SPEED, scrollTo) : Math.min(y + LEVEL_SPEED, scrollTo);
        }

    }
    private void handleX() {
        Room r = handler.currentRoom;
        float max_x = GRenderer.preCamDisplayX(r.CAM_MAX_X.x);

        float px = GRenderer.preCamDisplayX(handler.player.x);
        float ideal_x = - px;

        if (handler.player.velX != 0) {
            x_offset = handler.player.velX > 0 ? Math.max(x_offset - BASE_SPEED, CENTER_X - X_MAX_OFFSET) : Math.min(x_offset + BASE_SPEED, CENTER_X + X_MAX_OFFSET);
        }

        ideal_x += x_offset;

        if (ideal_x > 0) {
            x_offset = Math.max(x + px, CENTER_X - X_MAX_OFFSET);
            ideal_x = 0;
        }else if (ideal_x + max_x < GRenderer.G_WIDTH) {
            x_offset = Math.min(x + px, CENTER_X + X_MAX_OFFSET);
            ideal_x = Math.min(0, GRenderer.G_WIDTH - max_x);
        }

        x = ideal_x;
    }
}
