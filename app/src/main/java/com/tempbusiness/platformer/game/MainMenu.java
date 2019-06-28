package com.tempbusiness.platformer.game;

import com.tempbusiness.platformer.R;
import com.tempbusiness.platformer.background.AudioPlayer;
import com.tempbusiness.platformer.background.Game;
import com.tempbusiness.platformer.background.GameHandler;
import com.tempbusiness.platformer.background.Touchable;
import com.tempbusiness.platformer.graphicobjects.Box;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.load.FileLoader;

public class MainMenu extends GameHandler {
    public MainMenu(Game gameInstance) {
        this.game = gameInstance;
        audioPlayer = new AudioPlayer(this);
        graphics.add(new Box(0,0, Display.WIDTH, Display.HEIGHT, FileLoader.Image.BLOCK));

        Touchable touch = new Touchable(0,0,Display.WIDTH, Display.HEIGHT) {
            @Override
            public void downAction() {
                game.setHandler(new Platformer(game));
            }

            @Override
            public void upAction() {

            }
        };
        touchables.add(touch);
        audioPlayer.playMusic(FileLoader.Sound.TITLE,0,true);
    }
}
