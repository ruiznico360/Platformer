package com.tempbusiness.platformer.backup;

import android.graphics.Color;

public class Button extends Box {
    public Touchable area;
    public boolean visible = true;
    public GameHandler handler;

    public Button(int color, GameHandler handler, Touchable custom) {
        super(custom.x,custom.y,custom.w,custom.h,color);
        this.area = custom;
        this.handler = handler;

        handler.touchables.add(area);
    }
    public Button(FileLoader.Image img, GameHandler handler, Touchable custom) {
        super(custom.x,custom.y,custom.w,custom.h,img);
        this.area = custom;
        this.handler = handler;

        handler.touchables.add(area);

    }
    public void render(Renderer canvas) {
        if (visible) {
            super.render(canvas);

            if (area.touching) {
                canvas.drawRect(ImageUtil.rect(area.x, area.y, area.w, area.h), Color.argb(100, 0, 0, 0));
            }
        }
    }
}
