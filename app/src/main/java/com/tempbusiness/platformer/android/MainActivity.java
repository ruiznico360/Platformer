package com.tempbusiness.platformer.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.tempbusiness.platformer.game.Game;

public class MainActivity extends Activity {
    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = new Game(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        game.paused();
    }
    @Override
    protected void onResume() {
        super.onResume();
        game.resumed();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return game.dispatchKeyEvent(event);
    }
}
