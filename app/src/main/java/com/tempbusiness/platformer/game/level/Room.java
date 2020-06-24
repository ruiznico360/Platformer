package com.tempbusiness.platformer.game.level;

import android.graphics.Color;

import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.gameobject.Block;
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
        if (id.equals(ID.L1_1)) {
            int width = 50;
            handler.addBackgroundGraphic(new Box(0,0, Display.WIDTH,Display.HEIGHT, Color.CYAN));
            CAM_MAX_X = new Block(width, -10, Block.Type.BRICK, handler);
            CAM_Y_BOUNDS.add(new Block(-10, Platformer.BLOCKS_PER_Y-4, Block.Type.BRICK, handler));
            CAM_Y_BOUNDS.add(new Block(-10, Platformer.BLOCKS_PER_Y + 12, Block.Type.BRICK, handler));

            handler.addGameObject(CAM_MAX_X);

            for (Block b : CAM_Y_BOUNDS) {
                handler.addGameObject(b);
            }

            pStartX = 17;
            pStartY = 9f;

            handler.addGameObject(new Block.Warpzone(1,4,handler, new Room(handler, ID.L1_2)));
            handler.addGameObject(new Block.Warpzone(1,5,handler, null));

            for (int i = 0; i < width; i++) {
                handler.addGameObject(new Block(i,0, Block.Type.DIRT, handler));
                handler.addGameObject(new Block(i,1, Block.Type.DIRT, handler));
                handler.addGameObject(new Block(i,2, Block.Type.DIRT, handler));
                handler.addGameObject(new Block(i,3, Block.Type.GRASS, handler));

                handler.addGameObject(new Block(i,Platformer.BLOCKS_PER_Y - 1, Block.Type.BRICK, handler));
                handler.addGameObject(new Block(i,Platformer.BLOCKS_PER_Y + 15, Block.Type.BRICK, handler));
            }
            for (int i = 4; i < Platformer.BLOCKS_PER_Y + 16; i++) {
                handler.addGameObject(new Block(0,i, Block.Type.BRICK, handler));
                handler.addGameObject(new Block(width - 1,i, Block.Type.BRICK, handler));
            }

            handler.addGameObject(new Block(5,8, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(7,8, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(9,8, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(17,8, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(width - 2,5, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(width - 2,9, Block.Type.BRICK, handler));
            handler.addGameObject(new Block(width - 3,10, Block.Type.BRICK, handler));


        }else{
//            handler.addGameObject(new Box(0,0, Display.WIDTH, Display.HEIGHT, Color.CYAN));
//            CAM_MAX_X = new Block(Platformer.BLOCKS_PER_X - 1, -10, Block.Type.GRASS, handler);
//            CAM_MAX_Y = new Block(-10, 30-1, Block.Type.BRICK, handler);
//            handler.addGameObject(CAM_MAX_X);
//            handler.addGameObject(CAM_MAX_Y);
//
//            pStartX = 5;
//            pStartY = 6f;
//
//            handler.addGameObject(new Block.Type.Warpzone(1,1,handler, new Room(handler, ID.L1_1)));
//            handler.addGameObject(new Block.Type.Warpzone(1,2,handler, null));
//
//            for (int i = 0; i < Platformer.BLOCKS_PER_X; i++) {
//                handler.addGameObject(new Block(i,0, Block.Type.GRASS, handler));
//                handler.addGameObject(new Block(i, 30-1, Block.Type.BRICK, handler));
//            }
//            for (int i = 1; i < 30; i++) {
//                handler.addGameObject(new Block(0,i, Block.Type.BRICK, handler));
//                handler.addGameObject(new Block(Platformer.BLOCKS_PER_X  - 1,i, Block.Type.BRICK, handler));
//            }
//
//            handler.addGameObject(new Block(5,5, Block.Type.BRICK, handler));
//            handler.addGameObject(new Block(7,5, Block.Type.BRICK, handler));
//            handler.addGameObject(new Block(9,5, Block.Type.BRICK, handler));
//            handler.addGameObject(new Block(7,10, Block.Type.BRICK, handler));
//            handler.addGameObject(new Block(5,15, Block.Type.BRICK, handler));
//            handler.addGameObject(new Block(9,15, Block.Type.BRICK, handler));
//            handler.addGameObject(new Block(9,20, Block.Type.BRICK, handler));
//
//            handler.addGameObject(new Block.Type.Climbable(14,1, handler));
//            handler.addGameObject(new Block.Type.Climbable(14,2, handler));
//            handler.addGameObject(new Block.Type.Climbable(14,3, handler));
//
//            for (int i = 3; i < 30; i++) {
//                handler.addGameObject(new Block.Type.Climbable(15,i, handler));
//            }
//            handler.addGameObject(new Block(14,13, Block.Type.BRICK, handler));
//            handler.addGameObject(new Block(14,14, Block.Type.BRICK, handler));
//            handler.addGameObject(new Block(14,15, Block.Type.BRICK, handler));
//            handler.addGameObject(new Block(16,13, Block.Type.BRICK, handler));
//            handler.addGameObject(new Block(16,14, Block.Type.BRICK, handler));
//            handler.addGameObject(new Block(16,15, Block.Type.BRICK, handler));
//
//            for (int x = 1; x < 7; x++) {
//                for (int y = 18; y < 26; y++) {
//                    handler.addGameObject(new Block.Type.Water(x,y, handler));
//                }
//            }
//            handler.specialBlocks.waters.add(new int[]{1,18,6,8});
        }
    }
    public enum ID {
        L1_1, L1_2
    }
}
