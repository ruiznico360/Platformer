package com.tempbusiness.platformer.game.graphics.transition;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;
import com.tempbusiness.platformer.game.graphics.rendering.Renderer;
import com.tempbusiness.platformer.game.handler.Platformer;

public class BlackCircle extends Transition {
    private static Bitmap CIRCLE_RESOURCE;
    private static final int COLOR = Color.BLACK;
    private final int pX, pY, maxR;
    private final boolean closing;

    public BlackCircle (Platformer handler, Runnable end, float circleX, float circleY, float radius, boolean closing) {
        super(handler, end, closing ? Type.MARCH_BACKWARD : Type.MARCH_FORWARD);

        this.pX = (int) circleX;
        this.pY = (int) circleY;
        this.maxR = (int) radius;
        this.closing = closing;
    }

    protected int getDuration() {
        return 40;
    }

    public void render(Renderer renderer) {
        float radius = maxR * (closing ? (1 - (float)tickCount / getDuration()) : (float) tickCount / getDuration());
        float x = pX - radius;
        float y = pY - radius;
        float l = Display.OFFSET_SCREEN_X, t = Display.OFFSET_SCREEN_Y, w = GRenderer.G_WIDTH, h = GRenderer.G_HEIGHT;

        Rect circleDst = Display.rect(x, y, radius * 2, radius * 2);
        Rect[] boxes = new Rect[4];
        boxes[0] = Display.rect(l,t, x - l, h);
        boxes[1] = Display.rect(l,t, w, y - t);
        boxes[2] = Display.rect(l,y + 2 * radius, w, t + h - y);
        boxes[3] = Display.rect(x + 2 * radius,y, l + w - (x + 2 * radius), 2 * radius);

        renderer.drawBitmap(circleDst, CIRCLE_RESOURCE);

        for (Rect r : boxes) renderer.drawRect(r, COLOR);
    }

    public static void setupResources() {
        final int res = 100;
        CIRCLE_RESOURCE = Bitmap.createBitmap(res,res,Bitmap.Config.ARGB_8888);
        RectF outerRectangle = new RectF(0, 0, res, res);

        Canvas cCanvas = new Canvas(CIRCLE_RESOURCE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(COLOR);
        cCanvas.drawRect(outerRectangle, paint);

        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        cCanvas.drawCircle(CIRCLE_RESOURCE.getWidth() / 2, CIRCLE_RESOURCE.getHeight() / 2, CIRCLE_RESOURCE.getWidth() / 2, paint);
    }
}
