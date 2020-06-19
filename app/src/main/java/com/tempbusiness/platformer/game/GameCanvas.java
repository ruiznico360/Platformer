package com.tempbusiness.platformer.game;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tempbusiness.platformer.util.Transition;
import com.tempbusiness.platformer.util.Util;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback {
    private Game game;
    private Transition currentTransition;

    public GameCanvas(Game game) {
        super(game.getContext());
        this.game = game;

        getHolder().addCallback(this);
        setWillNotDraw(false);

        Transition.setupResources();
    }

    public void frame() {
        game.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });

    }
    @Override
    public void onDraw(Canvas canvas) {
        game.getHandler().tick();
        game.getHandler().ticksSinceStart++;

        handleGraphics(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.getTouchAdapter().handleTouchInput(event);
        return true;
    }
    private synchronized void handleGraphics(Canvas canvas) {
        game.getHandler().render(canvas);
    }
    public void surfaceDestroyed(SurfaceHolder holder) {}
    public void surfaceChanged(SurfaceHolder holder,int a,int b, int c) {}
    public void surfaceCreated(SurfaceHolder holder) {}
}
