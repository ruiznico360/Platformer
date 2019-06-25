package com.tempbusiness.platformer.background;

import android.app.Activity;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.tempbusiness.platformer.game.MainMenu;
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
        super.onPause();
        gameLoop.stop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        gameLoop.start();
    }

    @Override
    public void onBackPressed() {
//        Game.HANDLER.levelHandler.paused = !Game.HANDLER.levelHandler.paused;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Util.log("saving... " + handler);
        instance.vars.set(0, handler);
        outState.putSerializable(ACCESS_KEY, instance);
    }

    public void init(GameHandler prev) {
        Util.init(this);
        FileLoader.setup();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        touchAdapter = new TouchAdapter(this);
        handler = prev == null ? new MainMenu() : prev;
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
