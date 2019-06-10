package com.tempbusiness.platformer.background;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.tempbusiness.platformer.game.MainMenu;
import com.tempbusiness.platformer.graphics.GameCanvas;
import com.tempbusiness.platformer.load.FileLoader;
import com.tempbusiness.platformer.util.Util;

public class Game extends Activity {
    public TouchAdapter touchAdapter;
    public GameLoop gameLoop;
    public GameCanvas canvas;
    public GameHandler handler;
    public Handler ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    @Override
    protected void onPause() {
        super.onPause();
        gameLoop.PAUSE_LOOP = true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        gameLoop.PAUSE_LOOP = false;
    }

    @Override
    public void onBackPressed() {
//        Game.HANDLER.levelHandler.paused = !Game.HANDLER.levelHandler.paused;
    }

    public void init() {
        Util.init(this);
        FileLoader.setup();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        touchAdapter = new TouchAdapter(this);
        handler = new MainMenu(this);
        ui = new Handler();
        gameLoop = new GameLoop(this);
        gameLoop.start();
        canvas = new GameCanvas(this);
        setContentView(canvas);
    }
}
