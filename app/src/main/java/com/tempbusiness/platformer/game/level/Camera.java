package com.tempbusiness.platformer.game.level;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.graphics.Renderer;
import com.tempbusiness.platformer.util.Util;

public class Camera {
    public static float IDEAL_X_RIGHT, IDEAL_X_LEFT, IDEAL_Y_UP, IDEAL_Y_DOWN, X_SPEED, Y_SPEED;
    public Platformer handler;
    public float x,y, screen_x, screen_y, x_bound, y_bound;

    public Camera(Platformer handler) {
        this.handler = handler;
        IDEAL_X_RIGHT = Display.WIDTH / 2f - Display.BLOCK_SIZE * 2;
        IDEAL_X_LEFT = Display.WIDTH / 2f + Display.BLOCK_SIZE;
        IDEAL_Y_UP = Display.HEIGHT / 2f - Display.BLOCK_SIZE * 2;
        IDEAL_Y_DOWN = Display.HEIGHT / 2f + Display.BLOCK_SIZE;
        X_SPEED = Display.WIDTH / 400f;
        Y_SPEED = X_SPEED;
        screen_x = Display.WIDTH / 2;
        screen_y = Display.HEIGHT / 2;
        x_bound = Display.OFFSET_SCREEN_X + Display.G_WIDTH;
        y_bound = Display.OFFSET_SCREEN_Y;

    }

    public void tick(boolean force) {
        Room r = handler.currentRoom;
        float max_x = x_bound - Renderer.preCamDisplayX(r.CAM_MAX_X.x);
        float max_y = y_bound - Renderer.preCamDisplayY(r.CAM_MAX_Y.y);
        float min_x = 0;
        float min_y = 0;
        float ideal_x = screen_x - Renderer.preCamDisplayX(handler.player.x);
        float ideal_y = screen_y - Renderer.preCamDisplayY(handler.player.y);

        x = Math.min(Math.max(max_x,ideal_x), 0);
        y = Math.min(max_y,Math.max(ideal_y, 0));

        //        if (handler.player.velX != 0 || force) {
//            float player_pixel_x = handler.player.getHitbox().l.left;
//            float screenPosX = x + Display.OFFSET_SCREEN_X + player_pixel_x;
//            screen_x = handler.player.velX > 0 ? Math.max(screen_x - X_SPEED, IDEAL_X_RIGHT) : Math.min(screen_x + X_SPEED, IDEAL_X_LEFT);
//            float newX = screen_x - player_pixel_x;
//
//            if (newX > 0) {
//                newX = 0;
//                screen_x = Math.max(screenPosX, IDEAL_X_RIGHT);
//            }else if (newX + Display.OFFSET_SCREEN_X + (handler.currentRoom.CAM_MAX_X.getHitbox().r.right) < Display.WIDTH) {
//                newX = Math.min(0, Display.WIDTH - Display.OFFSET_SCREEN_X - (handler.currentRoom.CAM_MAX_X.getHitbox().r.right));
//                screen_x = Math.min(screenPosX, IDEAL_X_LEFT);
//            }
//
//            x = newX;
//        }
//        if (handler.player.velY != 0 || force) {
//            float player_pixel_y = handler.player.getHitbox().t.top;
//            float newY;
//
//            if (handler.player.velY > 0) {
//                if (IDEAL_Y_UP > y + player_pixel_y) {
//                    newY = IDEAL_Y_UP - player_pixel_y;
//                }else{
//                    newY = y;
//                }
//            }else{
//                if (IDEAL_Y_DOWN < y + player_pixel_y) {
//                    newY = IDEAL_Y_DOWN - player_pixel_y;
//                }else{
//                    newY = y;
//                }
//            }
//            if (newY < 0) {
//                newY = 0;
//            }else if (newY + (handler.currentRoom.CAM_MAX_Y.getHitbox().t.top) > 0) {
//                newY = Math.max(0, (-handler.currentRoom.CAM_MAX_Y.getHitbox().t.top));
//            }
//
//            y = newY;
//        }

    }
}
