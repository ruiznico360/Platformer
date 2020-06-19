package com.tempbusiness.platformer.game.graphics;

import android.graphics.Color;

import com.tempbusiness.platformer.game.handler.GameHandler;
import com.tempbusiness.platformer.game.controls.Touchable;
import com.tempbusiness.platformer.game.graphics.rendering.Renderer;
import com.tempbusiness.platformer.fileio.FileLoader;

public class Button extends Box{
    public Touchable area;
    public boolean visible = true;
    public GameHandler handler;

    public boolean touching() {
        return area.touching;
    }
    public Button(int color, GameHandler handler, Touchable custom) {
        super(custom.x,custom.y,custom.w,custom.h,color);
        this.area = custom;
        this.handler = handler;

        handler.touchables.add(area);
    }
    public Button(FileLoader.Image img,  GameHandler handler, Touchable custom) {
        super(custom.x,custom.y,custom.w,custom.h,img);
        this.area = custom;
        this.handler = handler;

        handler.touchables.add(area);

    }

    public void render(Renderer canvas) {
        if (visible) {
            super.render(canvas);

            if (area.touching) {
                canvas.drawRect(Display.rect(area.x, area.y, area.w, area.h), Color.argb(100, 0, 0, 0));
            }
        }
    }
}
