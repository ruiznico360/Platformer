package com.tempbusiness.platformer.game;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.tempbusiness.platformer.game.touch.TouchAdapter;
import com.tempbusiness.platformer.game.touch.Touchable;
import com.tempbusiness.platformer.game.handler.GameHandler;
import com.tempbusiness.platformer.game.handler.MainMenu;
import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.fileio.FileLoader;

public class Game {
    private Activity c;
    private TouchAdapter touchAdapter;
    private GameLoop gameLoop;
    private GameCanvas gameCanvas;
    private GameHandler gameHandler;
    private Handler ui;

    public Game(Activity c) {
        this.c = c;
        init();
    }

    public void paused() {
        gameLoop.stop();
    }

    public void resumed() {
        gameLoop.start();

//        soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC, 0);
//        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//            @Override
//            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                soundPool.play(sampleId,0.5f,0.5f,1,0,1);
//            }
//        });
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        Touchable area = Platformer.jump.area;
        if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            area = Platformer.left.area;
        }else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            area = Platformer.right.area;
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            area = Platformer.jump.area;
        }else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            area = Platformer.down.area;
        }

        if (event.getAction() == KeyEvent.ACTION_DOWN) area.down();
        else area.up();

        return true;
    }

    public TouchAdapter getTouchAdapter() {
        return touchAdapter;
    }

    public GameHandler getHandler() {
        return gameHandler;
    }

    public GameCanvas getCanvas() {
        return gameCanvas;
    }

    public Context getContext() {
        return c;
    }

    public void setHandler(GameHandler newHandler) {
//        if (handler.audioPlayer != null && newHandler.audioPlayer != null) {
//            for (AudioPlayer.MusicClip c : handler.audioPlayer.musicClips) {
//                if (c.bound) {
//                    c.stop(false);
//                }else{
//                    newHandler.audioPlayer.musicClips.add(c);
//                }
//            }
//        }
        gameHandler = newHandler;
    }

    public void runOnUIThread(Runnable r) {
        ui.post(r);
    }

    private void init() {
        Display.initDimens(c);
        FileLoader.setup(c);
        c.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ui = new Handler();
        gameHandler = new MainMenu(this);
        touchAdapter = new TouchAdapter(this);
        gameLoop = new GameLoop(this);
        gameCanvas = new GameCanvas(this);
        c.setContentView(gameCanvas);
    }
}
