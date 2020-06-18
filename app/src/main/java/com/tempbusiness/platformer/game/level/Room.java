package com.tempbusiness.platformer.game.level;

import android.graphics.Color;

import com.tempbusiness.platformer.game.Platformer;
import com.tempbusiness.platformer.graphicobjects.gameobject.Block;
import com.tempbusiness.platformer.graphicobjects.Box;
import com.tempbusiness.platformer.graphics.Display;

import java.util.ArrayList;

public class Room {
    public float pStartX, pStartY;
    public Block CAM_MAX_X;
    public ArrayList<Block> CAM_Y_BOUNDS = new ArrayList<>();
    public ID id;
    public Platformer handler;

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
            handler.addGraphic(new Box(0,0, Display.WIDTH, Display.HEIGHT, Color.CYAN));
            CAM_MAX_X = new Block(width, -10, Block.BRICK, handler);
            CAM_Y_BOUNDS.add(new Block(-10, Platformer.BLOCKS_PER_Y-1, Block.BRICK, handler));
            CAM_Y_BOUNDS.add(new Block(-10, Platformer.BLOCKS_PER_Y + 16, Block.BRICK, handler));

            handler.addGraphic(CAM_MAX_X);

            for (Block b : CAM_Y_BOUNDS) {
                handler.addGraphic(b);
            }

            pStartX = 17;
            pStartY = 7.4f;

            handler.addGraphic(new Block.Warpzone(1,1,handler, new Room(handler, ID.L1_2)));
            handler.addGraphic(new Block.Warpzone(1,2,handler, null));

            for (int i = 0; i < width - 2; i++) {
                handler.addGraphic(new Block(i,0, Block.BRICK, handler));
                handler.addGraphic(new Block(i,Platformer.BLOCKS_PER_Y - 1, Block.BRICK, handler));
                handler.addGraphic(new Block(i,Platformer.BLOCKS_PER_Y + 15, Block.BRICK, handler));
            }
            for (int i = 1; i < Platformer.BLOCKS_PER_Y + 16; i++) {
                handler.addGraphic(new Block(0,i, Block.BRICK, handler));
                handler.addGraphic(new Block(width - 1,i, Block.BRICK, handler));
            }

            handler.addGraphic(new Block(5,5, Block.BRICK, handler));
            handler.addGraphic(new Block(7,5, Block.BRICK, handler));
            handler.addGraphic(new Block(9,5, Block.BRICK, handler));
            handler.addGraphic(new Block(17,5, Block.BRICK, handler));
            handler.addGraphic(new Block(width - 2,2, Block.BRICK, handler));
        }else{
//            handler.addGraphic(new Box(0,0, Display.WIDTH, Display.HEIGHT, Color.CYAN));
//            CAM_MAX_X = new Block(Platformer.BLOCKS_PER_X - 1, -10, Block.GRASS, handler);
//            CAM_MAX_Y = new Block(-10, 30-1, Block.BRICK, handler);
//            handler.addGraphic(CAM_MAX_X);
//            handler.addGraphic(CAM_MAX_Y);
//
//            pStartX = 5;
//            pStartY = 6f;
//
//            handler.addGraphic(new Block.Warpzone(1,1,handler, new Room(handler, ID.L1_1)));
//            handler.addGraphic(new Block.Warpzone(1,2,handler, null));
//
//            for (int i = 0; i < Platformer.BLOCKS_PER_X; i++) {
//                handler.addGraphic(new Block(i,0, Block.GRASS, handler));
//                handler.addGraphic(new Block(i, 30-1, Block.BRICK, handler));
//            }
//            for (int i = 1; i < 30; i++) {
//                handler.addGraphic(new Block(0,i, Block.BRICK, handler));
//                handler.addGraphic(new Block(Platformer.BLOCKS_PER_X  - 1,i, Block.BRICK, handler));
//            }
//
//            handler.addGraphic(new Block(5,5, Block.BRICK, handler));
//            handler.addGraphic(new Block(7,5, Block.BRICK, handler));
//            handler.addGraphic(new Block(9,5, Block.BRICK, handler));
//            handler.addGraphic(new Block(7,10, Block.BRICK, handler));
//            handler.addGraphic(new Block(5,15, Block.BRICK, handler));
//            handler.addGraphic(new Block(9,15, Block.BRICK, handler));
//            handler.addGraphic(new Block(9,20, Block.BRICK, handler));
//
//            handler.addGraphic(new Block.Climbable(14,1, handler));
//            handler.addGraphic(new Block.Climbable(14,2, handler));
//            handler.addGraphic(new Block.Climbable(14,3, handler));
//
//            for (int i = 3; i < 30; i++) {
//                handler.addGraphic(new Block.Climbable(15,i, handler));
//            }
//            handler.addGraphic(new Block(14,13, Block.BRICK, handler));
//            handler.addGraphic(new Block(14,14, Block.BRICK, handler));
//            handler.addGraphic(new Block(14,15, Block.BRICK, handler));
//            handler.addGraphic(new Block(16,13, Block.BRICK, handler));
//            handler.addGraphic(new Block(16,14, Block.BRICK, handler));
//            handler.addGraphic(new Block(16,15, Block.BRICK, handler));
//
//            for (int x = 1; x < 7; x++) {
//                for (int y = 18; y < 26; y++) {
//                    handler.addGraphic(new Block.Water(x,y, handler));
//                }
//            }
//            handler.specialBlocks.waters.add(new int[]{1,18,6,8});
        }
    }
    public enum ID {
        L1_1, L1_2
    }
}
