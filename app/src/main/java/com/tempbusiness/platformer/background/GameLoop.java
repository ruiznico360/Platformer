package com.tempbusiness.platformer.background;

import com.tempbusiness.platformer.util.Util;

public class GameLoop implements Runnable {
    private boolean running = false;
    public Game game;
    public static final int FRAMES_PER_SECOND = 60;
    public boolean PAUSE_LOOP = true;
    private Thread thread;

    public GameLoop(Game game) {
        this.game = game;
    }
    public void tick(){
        game.handler.tick();
    }
    public void render() {
        game.canvas.invalidate();
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
        Util.log("Thread has begun");
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
            while(delta >= 1){
                if (!PAUSE_LOOP) {
                    tick();
                    render();
                }
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
}
