package com.tempbusiness.platformer.game;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback {
    private Game game;

    protected GameCanvas(Game game) {
        super(game.getContext());
        this.game = game;

        getHolder().addCallback(this);
        setWillNotDraw(false);

//        Transition.setupResources();
    }

    @Override
    public void onDraw(Canvas canvas) {
        tickGameHandler();
        handleGraphics(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.getTouchAdapter().handleTouchInput(event);
        return true;
    }

    protected void frame() {
        game.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });

    }

    private void tickGameHandler() {
        game.getHandler().tick();
        game.getHandler().ticksSinceStart++;
    }
    private synchronized void handleGraphics(Canvas canvas) {
        game.getHandler().render(canvas);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {}
    public void surfaceChanged(SurfaceHolder holder,int a,int b, int c) {}
    public void surfaceCreated(SurfaceHolder holder) {}
}
