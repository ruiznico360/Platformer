package com.tempbusiness.platformer.game.graphics;

import android.graphics.Color;

import com.tempbusiness.platformer.game.handler.GameHandler;
import com.tempbusiness.platformer.game.touch.Touchable;
import com.tempbusiness.platformer.game.graphics.rendering.Renderer;
import com.tempbusiness.platformer.fileio.FileLoader;

public class Button extends Box{
    public Touchable area;
    public boolean visible = true;
    public GameHandler handler;

    public boolean isInTouch() {
        return area.isInTouch();
    }
    public Button(int color, GameHandler handler, Touchable custom) {
        super(custom.x(),custom.y(),custom.w(),custom.h(),color);
        this.area = custom;
        this.handler = handler;

        handler.getTouchables().add(area);
    }
    public Button(FileLoader.Image img,  GameHandler handler, Touchable custom) {
        super(custom.x(),custom.y(),custom.w(),custom.h(),img);
        this.area = custom;
        this.handler = handler;

        handler.getTouchables().add(area);

    }

    public void render(Renderer canvas) {
        if (visible) {
            super.render(canvas);

            if (isInTouch()) {
                canvas.drawRect(area.bounds(), Color.argb(100, 0, 0, 0));
            }
        }
    }
}
