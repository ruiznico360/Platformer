package com.tempbusiness.platformer.game;

import com.tempbusiness.platformer.util.Util;

public class GameLoop implements Runnable {
    private static final int FRAMES_PER_SECOND = 60;
    private boolean running = false;
    public Game game;

    GameLoop(Game game) {
        this.game = game;
    }

    synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        new Thread(this).start();
    }
    public void run() {
        long lastTime = System.nanoTime();
        double ns = 1000000000 / FRAMES_PER_SECOND;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                game.getCanvas().frame();

                updates++;
                delta--;
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
                updates = 0;
            }
        }
    }
    void stop() {
        running = false;
    }
}
