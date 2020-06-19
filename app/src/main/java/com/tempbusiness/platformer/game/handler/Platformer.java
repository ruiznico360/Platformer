package com.tempbusiness.platformer.game.handler;

import android.graphics.Canvas;
import android.graphics.Color;

import com.tempbusiness.platformer.game.graphics.Box;
import com.tempbusiness.platformer.game.touch.Touchable;
import com.tempbusiness.platformer.game.Game;
import com.tempbusiness.platformer.game.level.Camera;
import com.tempbusiness.platformer.game.level.Room;
import com.tempbusiness.platformer.game.gameobject.Block;
import com.tempbusiness.platformer.game.graphics.Button;
import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.game.gameobject.GameObject;
import com.tempbusiness.platformer.game.gameobject.Player;
import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;

import java.util.ArrayList;
import java.util.List;

public class Platformer extends GameHandler {
    public final static int BLOCKS_PER_Y = 14, BLOCKS_PER_X = 25;
    private final int LAYER_SIZE, GAME_LAYER = 1;
    public static Button left, right, jump, up, down, action;
    private int BUTTON_SIZE;
    private Player player;
    private Room currentRoom;
    private Camera cam;
    private boolean canMove = true;
    private SpecialBlocks specialBlocks;


    public Platformer(Game gameInstance) {
        super(gameInstance, 3);

        this.LAYER_SIZE = graphics.totalLayers();
        this.renderer = new GRenderer();
        BUTTON_SIZE = (int) Math.min(Display.dpToPx(100, game.getContext()), GRenderer.BLOCK_SIZE * 3);
        this.cam = new Camera(this);

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

        setupBlackbars();
        setupControls();
    }
    public void superTick() {
        if (canMove) checkControls();
    }

    private void setupBlackbars() {
        addExternalGraphic(new Box(0,0,Display.OFFSET_SCREEN_X, Display.HEIGHT, Color.BLACK));
        addExternalGraphic(new Box(Display.WIDTH - Display.OFFSET_SCREEN_X,0,Display.OFFSET_SCREEN_X, Display.HEIGHT, Color.BLACK));
        addExternalGraphic(new Box(0,0, Display.WIDTH,Display.OFFSET_SCREEN_Y, Color.BLACK));
        addExternalGraphic(new Box(0,Display.HEIGHT - Display.OFFSET_SCREEN_Y,Display.WIDTH, Display.OFFSET_SCREEN_Y, Color.BLACK));
    }

    private void setupControls() {
        final float indent = Display.dpToPx(10, game.getContext());
        left = new Button(Color.argb(100,255,0,0), this,Touchable.basic(0, Display.HEIGHT - BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE));
        right = new Button(Color.argb(100,255,0,0),this, Touchable.basic(left.x + left.w + indent, left.y, BUTTON_SIZE, BUTTON_SIZE) );
        jump = new Button(Color.argb(100,255,0,0), this, Touchable.basic(Display.WIDTH - BUTTON_SIZE, left.y, BUTTON_SIZE, BUTTON_SIZE));
        down = new Button(Color.argb(100,255,0,0), this,Touchable.basic(jump.x, jump.y - BUTTON_SIZE - indent, BUTTON_SIZE, BUTTON_SIZE));
        down.visible = false;
        up = new Button(Color.argb(100,255,0,0), this,Touchable.basic(jump.x, down.y - BUTTON_SIZE - indent, BUTTON_SIZE, BUTTON_SIZE));
        up.visible = false;
        action = new Button(Color.argb(100,255,0,0), this,Touchable.basic(down.x, down.y, BUTTON_SIZE, BUTTON_SIZE));
        action.visible = false;

        addExternalGraphic(left);
        addExternalGraphic(right);
        addExternalGraphic(jump);
        addExternalGraphic(down);
        addExternalGraphic(up);
        addExternalGraphic(action);
    }

    private void checkControls() {
        float speed = 0.2f;
        if (left.isInTouch()) {
            player.velX = -speed;
        }else if (right.isInTouch()) {
            player.velX = speed;
        }else{
            player.velX = 0;
            player.accX = 0;
        }
        if (jump.isInTouch()) {
            player.velY = speed;
        }else if (down.isInTouch()) {
            player.velY = -speed;
        }else{
            player.velY = 0;
            player.accY = 0;
        }
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
    }

    public List<Graphic> getGameObjects() {
        return graphics.getLayer(GAME_LAYER);
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
