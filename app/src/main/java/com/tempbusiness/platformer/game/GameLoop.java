package com.tempbusiness.platformer.game;

import com.tempbusiness.platformer.game.Game;

public class GameLoop implements Runnable {
    public boolean running = false;
    public Game game;
    public static final int FRAMES_PER_SECOND = 60;
    private Thread thread;

    public GameLoop(Game game) {
        this.game = game;
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = FRAMES_PER_SECOND;
        double ns = 1000000000 / amountOfTicks;
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
    public void stop() {
        running = false;
    }
}
