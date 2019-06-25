package com.tempbusiness.platformer.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.tempbusiness.platformer.R;
import com.tempbusiness.platformer.util.ImageUtil;
import com.tempbusiness.platformer.util.Util;

public class Transition {
    public static Bitmap CIRCLE_RESOURCE;
    public static final float MIN_RADIUS = 1, CIRCLE_BORDER = 15;
    public int duration;
    public int startDelay = -1;
    public int counter = 0;
    public float circleRadius, circleX, circleY;
    public boolean close;
    public Runnable end;

    public Transition (float x, float y, float w, float h, int duration, boolean close) {
        this.circleX = x + w / 2;
        this.circleY = y - h / 2;
        this.duration = duration;
        this.close = close;
    }

    public static void setupResources() {
        CIRCLE_RESOURCE = Bitmap.createBitmap(500,500, Bitmap.Config.ARGB_8888);
        RectF outerRectangle = new RectF(0, 0, Display.WIDTH, Display.HEIGHT);

        Canvas cCanvas = new Canvas(CIRCLE_RESOURCE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        cCanvas.drawRect(outerRectangle, paint);

        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        cCanvas.drawCircle(CIRCLE_RESOURCE.getWidth() / 2, CIRCLE_RESOURCE.getHeight() / 2, CIRCLE_RESOURCE.getWidth() / 2 - CIRCLE_BORDER, paint);
    }

    public void tick() {
        if (startDelay > 0) {
            startDelay--;
            return;
        }

        if (counter != duration) {
            circleRadius = close ? Display.WIDTH + ((MIN_RADIUS - Display.WIDTH) / duration) * counter : MIN_RADIUS + ((Display.WIDTH - MIN_RADIUS) / duration) * counter;
            counter++;
        }
    }
    public void render(Canvas canvas) {
        Rect dst = ImageUtil.rect(circleX - circleRadius, circleY - circleRadius, circleRadius * 2, circleRadius * 2);
        Paint color = ImageUtil.color(Color.BLACK);
        canvas.drawRect(0,0,circleX - circleRadius + CIRCLE_BORDER, Display.HEIGHT, color);
        canvas.drawRect((circleX + circleRadius) - CIRCLE_BORDER,0,Display.WIDTH, Display.HEIGHT, color);
        canvas.drawRect(0,0,Display.WIDTH, circleY - circleRadius + CIRCLE_BORDER, color);
        canvas.drawRect(0,(circleY + circleRadius) - CIRCLE_BORDER,Display.WIDTH, Display.HEIGHT, color);
        canvas.drawBitmap(CIRCLE_RESOURCE, null,dst,null);
    }
}