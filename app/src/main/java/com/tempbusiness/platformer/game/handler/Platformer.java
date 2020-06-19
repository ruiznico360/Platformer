package com.tempbusiness.platformer.game.handler;

import android.graphics.Canvas;
import android.graphics.Color;

import com.tempbusiness.platformer.game.controls.Touchable;
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
import com.tempbusiness.platformer.game.graphics.rendering.Renderer;

import java.util.ArrayList;

public class Platformer extends GameHandler {
    public final static int BLOCKS_PER_Y = 14, BLOCKS_PER_X = 25;
    public static Button left, right, jump, up, down, action;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private int BUTTON_SIZE;
    public Player player;
    public Room currentRoom;
    public Camera cam;
    private boolean canMove = true;
    private SpecialBlocks specialBlocks;


    public Platformer(Game gameInstance) {
        super(gameInstance);

        this.renderer = new GRenderer();
        BUTTON_SIZE = (int) Math.min(Display.dpToPx(100, game.getContext()), GRenderer.BLOCK_SIZE * 3);
        this.cam = new Camera(this, BUTTON_SIZE);

        changeRoom(new Room(this, Room.ID.L1_1));
    }

    public void changeRoom(Room r) {
        graphics.clear();
        gameObjects.clear();

        specialBlocks = new SpecialBlocks();

        this.currentRoom = r;
        this.currentRoom.setup();

        player = new Player(currentRoom.pStartX, currentRoom.pStartY,this);
        graphics.add(player);
        cam.tick();

        setupControls();
    }
    public void superTick() {
        if (canMove) checkControls();
    }

    public void setupControls() {
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
    }

    private void renderControls(Renderer canvas) {
        left.render(canvas);
        right.render(canvas);
        up.render(canvas);
        down.render(canvas);
        jump.render(canvas);
        action.render(canvas);
    }

    public void checkControls() {
        float speed = 0.2f;
        if (left.touching()) {
            player.velX = -speed;
        }else if (right.touching()) {
            player.velX = speed;
        }else{
            player.velX = 0;
            player.accX = 0;
        }
        if (jump.touching()) {
            player.velY = speed;
        }else if (down.touching()) {
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
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).tick();
        }
    }

    private void gRender(GRenderer renderer) {
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).gRender(renderer);
        }
    }

    private void renderBlackBars(Renderer r) {
        r.drawRect(Display.rect(0,0,Display.OFFSET_SCREEN_X, Display.HEIGHT), Color.BLACK);
        r.drawRect(Display.rect(Display.WIDTH - Display.OFFSET_SCREEN_X,0,Display.OFFSET_SCREEN_X, Display.HEIGHT), Color.BLACK);
        r.drawRect(Display.rect(0,0, Display.WIDTH,Display.OFFSET_SCREEN_Y), Color.BLACK);
        r.drawRect(Display.rect(0,Display.HEIGHT - Display.OFFSET_SCREEN_Y,Display.WIDTH, Display.OFFSET_SCREEN_Y), Color.BLACK);
    }

    public void render(Canvas canvas) {
        GRenderer r = (GRenderer) renderWith(canvas);
        gRender(r);

        for (int i = 0; i < graphics.size(); i++) {
            graphics.get(i).render(r);
        }

        renderBlackBars(r);
        renderControls(r);
    }

    public void addGraphic(Graphic g) {
        if (g instanceof Block.Climbable) {
            specialBlocks.climbables.add((Block.Climbable)g);
        }else if (g instanceof Block.Warpzone) {
            specialBlocks.warpzones.add(((Block.Warpzone)g));
        }

        if (g instanceof GameObject) {
            gameObjects.add((GameObject)g);
        }else{
            graphics.add(g);
        }
    }

    public class SpecialBlocks {
        public ArrayList<Block.Climbable> climbables = new ArrayList<>();
        public ArrayList<int[]> waters = new ArrayList<>();
        public ArrayList<Block.Warpzone> warpzones = new ArrayList<>();
    }
}
