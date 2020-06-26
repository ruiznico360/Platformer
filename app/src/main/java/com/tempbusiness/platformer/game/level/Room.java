package com.tempbusiness.platformer.game.level;

import android.graphics.Color;

import com.tempbusiness.platformer.game.gameobject.block.Warpzone;
import com.tempbusiness.platformer.game.gameobject.player.Player;
import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.gameobject.block.Block;
import com.tempbusiness.platformer.game.graphics.Box;
import com.tempbusiness.platformer.game.graphics.Display;

import java.util.ArrayList;

public class Room {
    public float pStartX, pStartY;
    public Block CAM_MAX_X;
    public ArrayList<Block> CAM_Y_BOUNDS = new ArrayList<>();
    public ID id;
    public Platformer handler;
    public Controller.MoveState moveState = Controller.MoveState.WALK;

    public Room(Platformer handler, ID id) {
        this.id = id;
        this.handler = handler;
    }

    public void reset() {
        handler.changeRoom(new Room(handler,id));
    }

    public void setup() {
        int width = 50;

        if (id.equals(ID.L1_1)) {
            handler.addBackgroundGraphic(new Box(0,0, Display.WIDTH,Display.HEIGHT, Color.CYAN));
            CAM_MAX_X = new Block(width, -10, Block.Type.BRICK, handler);

//            CAM_Y_BOUNDS.add(new Block(-10, Platformer.BLOCKS_PER_Y-4, Block.Type.BRICK, handler));
            CAM_Y_BOUNDS.add(new Block(-10, Platformer.BLOCKS_PER_Y + 30, Block.Type.BRICK, handler));

            handler.addGameObject(CAM_MAX_X);

            for (Block b : CAM_Y_BOUNDS) {
                handler.addGameObject(b);
            }

            pStartX = 1 + (0.5f - Player.WIDTH / 2);
            pStartY = 4;

            handler.addGameObject(new Warpzone.Door(1,4,handler, new Room(handler, ID.L1_2)));
            handler.addGameObject(new Warpzone.Door(1,5,handler, null));

            for (int i = 0; i < width; i++) {
                handler.addGameObject(new Block(i,0, Block.Type.DIRT, handler));
                handler.addGameObject(new Block(i,1, Block.Type.DIRT, handler));
                handler.addGameObject(new Block(i,2, Block.Type.DIRT, handler));
                handler.addGameObject(new Block(i,3, Block.Type.GRASS, handler));

                if (i != width - 2) {
                    handler.addGameObject(new Block(i, Platformer.BLOCKS_PER_Y - 1, Block.Type.BRICK, handler));
                    handler.addGameObject(new Block(i, Platformer.BLOCKS_PER_Y + 15, Block.Type.BRICK, handler));
                }
            }
            for (int i = 4; i < Platformer.BLOCKS_PER_Y + 16; i++) {
                handler.addGameObject(new Block(0,i, Block.Type.BRICK, handler));
                handler.addGameObject(new Block(width - 1,i, Block.Type.BRICK, handler));
            }

            final int spacing = 6;
            for (int y = Platformer.BLOCKS_PER_Y - 1 + spacing; y < Platformer.BLOCKS_PER_Y + 15; y+=spacing) {
                for (int x = spacing; x < width; x+=spacing) {
                    handler.addGameObject(new Block(x,y,Block.Type.BRICK, handler));
                }
            }

            handler.addGameObject(new Block(5,8, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(7,8, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(9,8, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(17,8, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(width - 2,5, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(width - 2,9, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(width - 3,10, Block.Type.BRICK, handler));


        }else{
            handler.addBackgroundGraphic(new Box(0,0, Display.WIDTH,Display.HEIGHT, Color.CYAN));
            CAM_MAX_X = new Block(width, -10, Block.Type.BRICK, handler);
            CAM_Y_BOUNDS.add(new Block(-10, Platformer.BLOCKS_PER_Y + 30, Block.Type.BRICK, handler));

            handler.addGameObject(CAM_MAX_X);
            handler.addGameObject(CAM_Y_BOUNDS.get(0));

            pStartX = 1 + (0.5f - Player.WIDTH / 2);
            pStartY = 4;

            for (int i = 0; i < width; i++) {
                handler.addGameObject(new Block(i,3, Block.Type.BRICK, handler));
                handler.addGameObject(new Block(i,Platformer.BLOCKS_PER_Y + 30, Block.Type.BRICK, handler));
            }

            for (int i = 3; i < Platformer.BLOCKS_PER_Y + 30; i++) {
                handler.addGameObject(new Block(0,i, Block.Type.BRICK, handler));
                handler.addGameObject(new Block(width - 1,i, Block.Type.BRICK, handler));
            }
            handler.addGameObject(new Warpzone.Door(1,4,handler, new Room(handler, ID.L1_1)));
            handler.addGameObject(new Warpzone.Door(1,5,handler, null));
        }
    }
    public enum ID {
        L1_1, L1_2
    }
}
