package com.tempbusiness.platformer.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tempbusiness.platformer.background.Game;
import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback {
    private Game game;
    private Renderer renderer;
    private Transition currentTransition;

    public GameCanvas(Game game) {
        super(game);
        this.game = game;
        this.renderer = new Renderer();

        getHolder().addCallback(this);
        setWillNotDraw(false);

        Transition.setupResources();
    }

    @Override
    public void onDraw(Canvas canvas) {
        game.gameLoop.tick();
        handleGraphics(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.touchAdapter.handleTouchInput(event);
        return true;
    }
    private synchronized void handleGraphics(Canvas canvas) {
        renderer.setCanvas(canvas);
        game.handler.render(renderer);
    }
    public void surfaceDestroyed(SurfaceHolder holder) {}
    public void surfaceChanged(SurfaceHolder holder,int a,int b, int c) {}
    public void surfaceCreated(SurfaceHolder holder) {}
}
