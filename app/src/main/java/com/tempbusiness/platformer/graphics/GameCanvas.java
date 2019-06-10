package com.tempbusiness.platformer.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tempbusiness.platformer.background.Game;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback {
    public Game game;
    public GameCanvas(Game game) {
        super(Util.appContext);
        this.game = game;
        getHolder().addCallback(this);
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        handleGraphics(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Game.CONTROLS.handleTouchInput(event);
        return true;
    }
    private synchronized void handleGraphics(Canvas canvas) {
        game.handler.render(canvas);
    }
    public void surfaceDestroyed(SurfaceHolder holder) {}
    public void surfaceChanged(SurfaceHolder holder,int a,int b, int c) {}
    public void surfaceCreated(SurfaceHolder holder) {}
}
