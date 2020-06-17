package com.tempbusiness.platformer.background;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.tempbusiness.platformer.game.MainMenu;
import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.graphics.GameCanvas;
import com.tempbusiness.platformer.load.FileLoader;
import com.tempbusiness.platformer.util.Util;

import java.io.Serializable;
import java.util.ArrayList;

public class Game extends Activity {
    public static final String ACCESS_KEY = "ACCESS_KEY";
    public TouchAdapter touchAdapter;
    public GameLoop gameLoop;
    public GameCanvas canvas;
    public GameHandler handler;
    public Handler ui;
    public SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }
    @Override
    protected void onPause() {
        super.onPause();
        gameLoop.stop();

        for (AudioPlayer.MusicClip c : handler.audioPlayer.musicClips) {
            c.stop(true);
        }

        soundPool.release();
    }
    @Override
    protected void onResume() {
        super.onResume();
        gameLoop.start();

        soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId,0.5f,0.5f,1,0,1);
            }
        });
    }

    @Override
    public void onBackPressed() {
//        Game.HANDLER.levelHandler.paused = !Game.HANDLER.levelHandler.paused;
    }

    @Override
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void setHandler(GameHandler newHandler) {
        if (handler.audioPlayer != null && newHandler.audioPlayer != null) {
            for (AudioPlayer.MusicClip c : handler.audioPlayer.musicClips) {
                if (c.bound) {
                    c.stop(false);
                }else{
                    newHandler.audioPlayer.musicClips.add(c);
                }
            }
        }
        handler = newHandler;
    }

    public void init() {
        Display.initDimens(this);
        FileLoader.setup(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        handler = new MainMenu(this);
        touchAdapter = new TouchAdapter(this);
        handler.game = this;
        ui = new Handler();
        gameLoop = new GameLoop(this);
        canvas = new GameCanvas(this);
        setContentView(canvas);
    }
}
