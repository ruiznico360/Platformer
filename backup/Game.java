package com.tempbusiness.platformer.backup;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

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
    public Instance instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            init(null);
        }else{
            init((GameHandler)((Instance)savedInstanceState.getSerializable(ACCESS_KEY)).vars.get(0));
        }
    }
    @Override
    protected void onPause() {
        Util.log("onpause");
        super.onPause();
        gameLoop.stop();

        for (AudioPlayer.MusicClip c : handler.audioPlayer.musicClips) {
            Util.log("stopping music");
            c.stop(true);
        }

        soundPool.release();
    }
    @Override
    protected void onResume() {
        Util.log("onresume");
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        instance.vars.set(0, handler);
        outState.putSerializable(ACCESS_KEY, instance);
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

    public void init(GameHandler prev) {
        Util.init(this);
        FileLoader.setup();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        touchAdapter = new TouchAdapter(this);
        handler = prev == null ? new MainMenu(this) : prev;
        handler.game = this;
        ui = new Handler();
        gameLoop = new GameLoop(this);
        canvas = new GameCanvas(this);
        setContentView(canvas);

        instance = new Instance();
        instance.vars.add(handler);
    }
    public static class Instance implements Serializable {
        public transient ArrayList<Object> vars = new ArrayList<>();

    }
}
