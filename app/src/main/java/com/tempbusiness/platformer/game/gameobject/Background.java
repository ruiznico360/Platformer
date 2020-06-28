package com.tempbusiness.platformer.game.gameobject;

import android.graphics.Rect;

import com.tempbusiness.platformer.resources.Image;
import com.tempbusiness.platformer.game.graphics.Box;
import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;
import com.tempbusiness.platformer.game.graphics.rendering.Renderer;
import com.tempbusiness.platformer.game.handler.Platformer;

public class Background extends Box {
    private Platformer handler;

    public Background(Image img, Platformer handler) {
        super(-1,-1,-1,-1,img);
        this.handler = handler;
    }

    public void render(Renderer renderer) {
        float camMaxX = handler.getCurrentRoom().CAM_MAX_X.x;
        float pX = handler.getPlayer().x;
        float width = Display.scaledWidthForBitmap(GRenderer.G_HEIGHT, Image.BACKGROUND.getImage());
        float excess = width - GRenderer.G_WIDTH;



        Rect dst = Display.rect(Display.OFFSET_SCREEN_X + excess * -(pX / camMaxX), Display.OFFSET_SCREEN_Y, width, GRenderer.G_HEIGHT);
        renderer.drawBitmap(dst, Image.BACKGROUND.getImage());
    }
}
