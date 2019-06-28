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
import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback {
    public Game game;
    public Transition currentTransition;

    public GameCanvas(Game game) {
        super(Util.appContext);
        this.game = game;
        getHolder().addCallback(this);
        setWillNotDraw(false);

        Transition.setupResources();
    }

    @Override
    public void onDraw(Canvas canvas) {
        game.handler.audioPlayer.tick();
        if (currentTransition == null) {
            game.gameLoop.tick();
            handleGraphics(new Renderer(canvas));
        } else {
            currentTransition.tick();
            handleGraphics(new Renderer(canvas));
            currentTransition.render(canvas);

            if (currentTransition.counter == currentTransition.duration) {
                Runnable r = currentTransition.end;
                currentTransition = null;
                if (r != null) r.run();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.touchAdapter.handleTouchInput(event);
        return true;
    }
    private synchronized void handleGraphics(Renderer canvas) {
        game.handler.render(canvas);
    }
    public void surfaceDestroyed(SurfaceHolder holder) {}
    public void surfaceChanged(SurfaceHolder holder,int a,int b, int c) {}
    public void surfaceCreated(SurfaceHolder holder) {}
}
