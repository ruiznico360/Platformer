package com.tempbusiness.platformer.backup;

public class MainMenu extends GameHandler {
    public MainMenu(Game gameInstance) {
        this.game = gameInstance;
        audioPlayer = new AudioPlayer(this);
        graphics.add(new Box(0,0, Display.WIDTH, Display.HEIGHT, FileLoader.Image.BLOCK));

        Touchable touch = new Touchable(0,0, Display.WIDTH, Display.HEIGHT) {
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
