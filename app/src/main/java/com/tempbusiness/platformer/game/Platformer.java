package com.tempbusiness.platformer.game;

import android.graphics.Color;

import com.tempbusiness.platformer.background.Game;
import com.tempbusiness.platformer.background.GameHandler;
import com.tempbusiness.platformer.background.Touchable;
import com.tempbusiness.platformer.graphicobjects.Block;
import com.tempbusiness.platformer.graphicobjects.Box;
import com.tempbusiness.platformer.graphicobjects.Button;
import com.tempbusiness.platformer.graphicobjects.Entity;
import com.tempbusiness.platformer.graphicobjects.Player;
import com.tempbusiness.platformer.graphics.Display;

public class Platformer extends GameHandler {
    public final static int BLOCKS_PER_SCREEN = 20, BUTTON_SIZE = 300;
    public Button left, right, up;
    public Player player;

    public Platformer(Game gameInstance) {
        super(gameInstance);

        gameObjects.add(new Box(0,0, Display.WIDTH, Display.HEIGHT, Color.CYAN));

        for (int i = 0; i < BLOCKS_PER_SCREEN; i++) {
            gameObjects.add(new Block(i,0, Block.GRASS, this));
            gameObjects.add(new Block(i,10, Block.BRICK, this));
        }
        for (int i = 1; i < 10; i++) {
            gameObjects.add(new Block(0,i, Block.BRICK, this));
            gameObjects.add(new Block(BLOCKS_PER_SCREEN - 1,i, Block.BRICK, this));
        }

        gameObjects.add(new Block(5,5, Block.BRICK, this));
        gameObjects.add(new Block(7,5, Block.BRICK, this));
        gameObjects.add(new Block(9,5, Block.BRICK, this));
        gameObjects.add(new Block(17,5, Block.BRICK, this));


        player = new Player(1,5,this);
        gameObjects.add(player);

        setupControls();
    }
    public void superTick() {
        checkControls();
    }

    public void setupControls() {
        left = new Button(Color.argb(100,255,0,0), this,new Touchable(25, Display.HEIGHT - BUTTON_SIZE - 75, BUTTON_SIZE, BUTTON_SIZE) {
            @Override public void downAction() { }@Override public void upAction() { }
        });
        right = new Button(Color.argb(100,255,0,0),this, new Touchable(BUTTON_SIZE + 75, Display.HEIGHT - BUTTON_SIZE - 75, BUTTON_SIZE, BUTTON_SIZE) {
            @Override public void downAction() { }@Override public void upAction() { }
        });
        up = new Button(Color.argb(100,255,0,0), this, new Touchable(Display.WIDTH - BUTTON_SIZE - 25, Display.HEIGHT - BUTTON_SIZE - 75, BUTTON_SIZE, BUTTON_SIZE) {
            @Override public void downAction() { }@Override public void upAction() { }
        });
        gameObjects.add(left);
        gameObjects.add(right);
        gameObjects.add(up);

    }

    public void checkControls() {
        if (left.area.touching) {
            player.velX = -0.05f;
        }else if (right.area.touching) {
            player.velX = 0.05f;
        }else{
            player.velX = 0;
        }
        if (up.area.touching) {
            player.velY = 0.1f;
        }
    }
    public void tick() {
        superTick();

        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i) instanceof Entity) {
                ((Entity) gameObjects.get(i)).gravity();
            }
            gameObjects.get(i).tick();
        }
    }
}
