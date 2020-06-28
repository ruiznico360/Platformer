package com.tempbusiness.platformer.game.handler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import com.tempbusiness.platformer.game.gameobject.Background;
import com.tempbusiness.platformer.game.gameobject.block.Warpzone;
import com.tempbusiness.platformer.game.graphics.Box;
import com.tempbusiness.platformer.game.graphics.transition.BlackCircle;
import com.tempbusiness.platformer.game.level.Controller;
import com.tempbusiness.platformer.game.Game;
import com.tempbusiness.platformer.game.level.Camera;
import com.tempbusiness.platformer.game.level.Room;
import com.tempbusiness.platformer.game.gameobject.block.Block;
import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.game.gameobject.GameObject;
import com.tempbusiness.platformer.game.gameobject.player.Player;
import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;
import com.tempbusiness.platformer.game.graphics.transition.Transition;

import java.util.ArrayList;
import java.util.List;

public class Platformer extends GameHandler {
    public final static int BLOCKS_PER_Y = 14, BLOCKS_PER_X = 25;
    private static final int LAYER_SIZE = 3, GAME_LAYER = 1;
    private Player player;
    private Room currentRoom;
    private Camera cam;
    private Controller controller;
    private SpecialBlocks specialBlocks;
    private Transition transition;


    public Platformer(Game gameInstance) {
        super(gameInstance, LAYER_SIZE);

        this.renderer = new GRenderer();
        this.cam = new Camera(this);
        this.controller = new Controller(this);


        setupCurrentRoom(new Room(this, Room.ID.L1_1));
    }

    public void changeRoom(final Room room) {
        float pX = GRenderer.displayX(player.x + player.w / 2, this);
        float pY = GRenderer.displayY(player.y + player.h / 2,this);

        float r = GRenderer.farthestEdgeDist(pX, pY);

        startTransition(new BlackCircle(this, new Runnable() {
            @Override
            public void run() {
                setupCurrentRoom(room);
            }
        }, pX, pY, r, true));
    }

    private void setupCurrentRoom(Room room) {
        graphics.clear();

        specialBlocks = new SpecialBlocks();

        this.currentRoom = room;
        this.currentRoom.setup();
        loadResources();

        player = new Player(currentRoom.pStartX, currentRoom.pStartY,this);
        addGameObject(player);

        cam.update(player, currentRoom);
        controller.update(player);

        setupBlackbars();
        controller.setupButtons();
        controller.setMoveState(currentRoom.moveState);
        gTick();

        float pX = GRenderer.displayX(player.x + player.w / 2, this);
        float pY = GRenderer.displayY(player.y + player.h / 2,this);
        float r = GRenderer.farthestEdgeDist(pX, pY);
        Transition t = new BlackCircle(this, new Runnable() {
            @Override
            public void run() {

            }
        }, pX, pY, r, false);

        t.setDelay(30);

        startTransition(t);
    }

    public boolean superTick() {
        if (transition == null) {
            controller.checkControls();
            return true;
        }

        transition.tick();
        return false;
    }

    private void setupBlackbars() {
        addExternalGraphic(new Box(0,0,Display.OFFSET_SCREEN_X, Display.HEIGHT, Color.BLACK));
        addExternalGraphic(new Box(Display.WIDTH - Display.OFFSET_SCREEN_X,0,Display.OFFSET_SCREEN_X, Display.HEIGHT, Color.BLACK));
        addExternalGraphic(new Box(0,0, Display.WIDTH,Display.OFFSET_SCREEN_Y, Color.BLACK));
        addExternalGraphic(new Box(0,Display.HEIGHT - Display.OFFSET_SCREEN_Y,Display.WIDTH, Display.OFFSET_SCREEN_Y, Color.BLACK));
    }

    public void tick() {
        if (superTick()) gTick();
    }

    private void gTick() {
        for (int i = 0; i < getGameObjects().size(); i++) {
            ((GameObject)getGameObjects().get(i)).tick();
        }

        specialBlockCollision();
        cam.tick();
    }

    public void startTransition(Transition t) {
        this.transition = t;
        controller.setVisibility(false);
        t.start();
    }

    public Transition getTransition() {
        return transition;
    }

    public void endTransition(Runnable run) {
        transition = null;
        controller.setVisibility(true);
        run.run();
    }

    private void specialBlockCollision() {
        boolean canClimb = false;
        controller.setTargetWarpzone(null);

        for (Block.Climbable b : specialBlocks.climbables) {
            if (b.getHitbox().intersects(player.getHitbox())) {
                canClimb = true;
                break;
            }
        }
        for (Warpzone b : specialBlocks.warpzones) if (b.usable(player)) controller.setTargetWarpzone(b);

//        for (Block.Climbable b : specialBlocks.climbables) {
//            if (b.getHitbox().intersects(player.getHitbox())) {
//                canClimb = true;
//                break;
//            }
//        }
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

        if (transition != null) transition.render(r);
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

    public Room getCurrentRoom() {
        return currentRoom;
    }
    public Player getPlayer() {
        return player;
    }

    protected List<String> requiredResources() {
        return currentRoom.getRequiredImages();
    }

    public void addGameObject(GameObject g) {
        if (g instanceof Block.Climbable) {
            specialBlocks.climbables.add((Block.Climbable)g);
        }else if (g instanceof Warpzone) {
            specialBlocks.warpzones.add(((Warpzone)g));
        }

        graphics.add(g, 1);
    }
    public void addExternalGraphic(Graphic g) {
        graphics.add(g, 2);
    }
    public void addBackgroundGraphic(Background b) {
        graphics.add(b, 0);
    }

    private class SpecialBlocks {
        private ArrayList<Block.Climbable> climbables = new ArrayList<>();
        private ArrayList<int[]> waters = new ArrayList<>();
        private ArrayList<Warpzone> warpzones = new ArrayList<>();
    }
}
