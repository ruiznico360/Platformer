package com.tempbusiness.platformer.game.handler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import com.tempbusiness.platformer.game.gameobject.player.PlayerWalkPhysics;
import com.tempbusiness.platformer.game.graphics.Box;
import com.tempbusiness.platformer.game.level.Controller;
import com.tempbusiness.platformer.game.Game;
import com.tempbusiness.platformer.game.level.Camera;
import com.tempbusiness.platformer.game.level.Room;
import com.tempbusiness.platformer.game.gameobject.Block;
import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.game.gameobject.GameObject;
import com.tempbusiness.platformer.game.gameobject.player.Player;
import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;

import java.util.ArrayList;
import java.util.List;

public class Platformer extends GameHandler {
    public final static int BLOCKS_PER_Y = 14, BLOCKS_PER_X = 25;
    private final int LAYER_SIZE, GAME_LAYER = 1;
    private Player player;
    private Room currentRoom;
    private Camera cam;
    private Controller controller;
    private SpecialBlocks specialBlocks;


    public Platformer(Game gameInstance) {
        super(gameInstance, 3);

        this.LAYER_SIZE = graphics.totalLayers();
        this.renderer = new GRenderer();
        this.cam = new Camera(this);
        this.controller = new Controller(this);

        changeRoom(new Room(this, Room.ID.L1_1));
    }

    public void changeRoom(Room r) {
        graphics.clear();

        specialBlocks = new SpecialBlocks();

        this.currentRoom = r;
        this.currentRoom.setup();

        player = new Player(currentRoom.pStartX, currentRoom.pStartY,this);
        addGameObject(player);

        cam.update(player, currentRoom);
        controller.update(player);

        setupBlackbars();
        controller.setupButtons();
        controller.setMoveState(r.moveState);
    }
    public void superTick() {
        controller.checkControls();
    }

    private void setupBlackbars() {
        addExternalGraphic(new Box(0,0,Display.OFFSET_SCREEN_X, Display.HEIGHT, Color.BLACK));
        addExternalGraphic(new Box(Display.WIDTH - Display.OFFSET_SCREEN_X,0,Display.OFFSET_SCREEN_X, Display.HEIGHT, Color.BLACK));
        addExternalGraphic(new Box(0,0, Display.WIDTH,Display.OFFSET_SCREEN_Y, Color.BLACK));
        addExternalGraphic(new Box(0,Display.HEIGHT - Display.OFFSET_SCREEN_Y,Display.WIDTH, Display.OFFSET_SCREEN_Y, Color.BLACK));
    }

    public void tick() {
        superTick();
        gTick();
        cam.tick();
    }

    private void gTick() {
        for (int i = 0; i < getGameObjects().size(); i++) {
            ((GameObject)getGameObjects().get(i)).tick();
        }
    }

    public void render(Canvas canvas) {
        GRenderer r = (GRenderer) renderWith(canvas);

        for (int i = 0; i < LAYER_SIZE; i++) {
            if (i == GAME_LAYER) {
                for (int p = 0; p < graphics.layerSize(i); p++) ((GameObject) graphics.get(p, i)).gRender(r);
            }else{
                for (int p = 0; p < graphics.layerSize(i); p++) graphics.get(p, i).render(r);

            }
        }

//        PlayerWalkPhysics.debugPMeterRender(r);
    }

    public List<Graphic> getGameObjects() {
        return graphics.getLayer(GAME_LAYER);
    }

    public Context getContext() {
        return game.getContext();
    }
    public Camera getCam() {
        return cam;
    }

    public void addGameObject(GameObject g) {
        if (g instanceof Block.Climbable) {
            specialBlocks.climbables.add((Block.Climbable)g);
        }else if (g instanceof Block.Warpzone) {
            specialBlocks.warpzones.add(((Block.Warpzone)g));
        }

        graphics.add(g, 1);
    }
    public void addExternalGraphic(Graphic g) {
        graphics.add(g, 2);
    }
    public void addBackgroundGraphic(Graphic g) {
        graphics.add(g, 0);
    }

    private class SpecialBlocks {
        private ArrayList<Block.Climbable> climbables = new ArrayList<>();
        private ArrayList<int[]> waters = new ArrayList<>();
        private ArrayList<Block.Warpzone> warpzones = new ArrayList<>();
    }
}
