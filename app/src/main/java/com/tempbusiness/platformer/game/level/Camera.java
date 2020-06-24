package com.tempbusiness.platformer.game.level;

import com.tempbusiness.platformer.game.gameobject.Entity;
import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.gameobject.Block;
import com.tempbusiness.platformer.game.gameobject.player.Player;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;

public class Camera {
    private final float Y_MAX_OFFSET, X_MAX_OFFSET, BASE_SPEED, CENTER_X, CENTER_Y, LEVEL_MIN_SPEED;
    private Platformer handler;
    private int controlS;
    private float x,y, y_offset, x_offset;
    private Player player;
    private Room room;

    public Camera(Platformer handler) {
        this.handler = handler;
        X_MAX_OFFSET = GRenderer.G_WIDTH * 0.1f;
        Y_MAX_OFFSET = GRenderer.G_HEIGHT * 0.1f;
        BASE_SPEED = GRenderer.G_WIDTH / 600f;
        LEVEL_MIN_SPEED = BASE_SPEED * 4;
        CENTER_X = GRenderer.G_WIDTH / 2;
        CENTER_Y = GRenderer.G_HEIGHT / 2;
        x_offset = CENTER_X;
        y_offset = CENTER_Y;
    }

    public void update(Player p, Room r) {
        this.player = p;
        this.room = r;
        tick();
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
        if (room.CAM_Y_BOUNDS.size() == 1) {
            handleUnboundedY();
        }else{
            handleLevelY();
        }
    }

    private void handleUnboundedY() {
        Room r = room;
        float max_y = GRenderer.preCamDisplayY(r.CAM_Y_BOUNDS.get(0).y);
        float py = GRenderer.preCamDisplayY(player.y);
        float ideal_y = - py;

        if (player.velY != 0) {
            y_offset = player.velY < 0 ? Math.max(y_offset - BASE_SPEED, CENTER_Y - Y_MAX_OFFSET) : Math.min(y_offset + BASE_SPEED, CENTER_Y + Y_MAX_OFFSET);
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
        Room r = room;
        Player p = player;

        float scrollTo = 0;
        for (Block b : r.CAM_Y_BOUNDS) {
            if (p.y < b.y) break;
            scrollTo = -GRenderer.preCamDisplayY(b.y);
        }

        float speed = LEVEL_MIN_SPEED;

        if (y != scrollTo) {
            y = y > scrollTo ? Math.max(y - speed, scrollTo) : Math.min(y + speed, scrollTo);
        }

    }
    private void handleX() {
        Room r = room;
        float max_x = GRenderer.preCamDisplayX(r.CAM_MAX_X.x);

        float px = GRenderer.preCamDisplayX(player.x);
        float ideal_x = - px;

        if (player.velX != 0) {
            x_offset = player.velX > 0 ? Math.max(x_offset - BASE_SPEED, CENTER_X - X_MAX_OFFSET) : Math.min(x_offset + BASE_SPEED, CENTER_X + X_MAX_OFFSET);
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
