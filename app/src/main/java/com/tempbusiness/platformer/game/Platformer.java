package com.tempbusiness.platformer.game;

import android.graphics.Color;

import com.tempbusiness.platformer.background.AudioPlayer;
import com.tempbusiness.platformer.background.Game;
import com.tempbusiness.platformer.background.GameHandler;
import com.tempbusiness.platformer.background.Touchable;
import com.tempbusiness.platformer.game.level.Camera;
import com.tempbusiness.platformer.game.level.Room;
import com.tempbusiness.platformer.graphicobjects.gameobject.Block;
import com.tempbusiness.platformer.graphicobjects.Button;
import com.tempbusiness.platformer.graphicobjects.Graphic;
import com.tempbusiness.platformer.graphicobjects.gameobject.Player;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.graphics.Renderer;

import java.util.ArrayList;

public class Platformer extends GameHandler {
    public final static int BLOCKS_PER_Y = 14, BLOCKS_PER_X = 25, BUTTON_SIZE = 300;
    public static Button left, right, jump, up, down, action;
    public Player player;
    public Room currentRoom;
    public Camera cam;
    public boolean canMove = true;
    public SpecialBlocks specialBlocks;


    public Platformer(Game gameInstance) {
        this.audioPlayer = new AudioPlayer(this);
        this.game = gameInstance;
        this.cam = new Camera(this);

       changeRoom(new Room(this, Room.ID.L1_1));
//       audioPlayer.playMusic(FileLoader.Sound.OVERWORLD);
    }

    public void changeRoom(Room r) {
        graphics.clear();
        specialBlocks = new SpecialBlocks();

        this.currentRoom = r;
        this.currentRoom.setup();

        player = new Player(currentRoom.pStartX, currentRoom.pStartY,this);
        graphics.add(player);
        cam.tick(true);

        setupControls();
    }
    public void superTick() {
        if (canMove) checkControls();
    }

    public void setupControls() {
        left = new Button(Color.argb(100,255,0,0), this,Touchable.basic(25, Display.HEIGHT - BUTTON_SIZE - 75, BUTTON_SIZE, BUTTON_SIZE));
        right = new Button(Color.argb(100,255,0,0),this, Touchable.basic(BUTTON_SIZE + 75, Display.HEIGHT - BUTTON_SIZE - 75, BUTTON_SIZE, BUTTON_SIZE) );
        down = new Button(Color.argb(100,255,0,0), this,Touchable.basic(Display.WIDTH - BUTTON_SIZE - 25, Display.HEIGHT - BUTTON_SIZE * 2 - 75 * 2, BUTTON_SIZE, BUTTON_SIZE));
        down.visible = false;
        up = new Button(Color.argb(100,255,0,0), this,Touchable.basic(Display.WIDTH - BUTTON_SIZE - 25, Display.HEIGHT - BUTTON_SIZE * 3 - 75 * 3, BUTTON_SIZE, BUTTON_SIZE));
        up.visible = false;
        action = new Button(Color.argb(100,255,0,0), this,Touchable.basic(down.x, down.y, BUTTON_SIZE, BUTTON_SIZE));
        action.visible = false;

        jump = new Button(Color.argb(100,255,0,0), this, Touchable.basic(Display.WIDTH - BUTTON_SIZE - 25, Display.HEIGHT - BUTTON_SIZE - 75, BUTTON_SIZE, BUTTON_SIZE));
//        if (Display.WIDTH <= 2560) {
//            jump = new Button(Color.argb(100,255,0,0), this, Touchable.basic(Display.WIDTH - BUTTON_SIZE - 25, Display.HEIGHT - BUTTON_SIZE - 75, BUTTON_SIZE, BUTTON_SIZE));
//        }else{
//            jump = new Button(Color.argb(100,255,0,0), this, Touchable.basic(right.x, left.y - BUTTON_SIZE - 75, BUTTON_SIZE, BUTTON_SIZE));
//        }
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

        for (int i = 0; i < graphics.size(); i++) {
            graphics.get(i).tick();
        }

        cam.tick(false);
    }

    public void render(Renderer canvas) {
        super.render(canvas);

        canvas.drawRect(Display.rect(0,0,Display.OFFSET_SCREEN_X, Display.HEIGHT), Color.BLACK);
        canvas.drawRect(Display.rect(Display.WIDTH - Display.OFFSET_SCREEN_X,0,Display.OFFSET_SCREEN_X, Display.HEIGHT), Color.BLACK);
        canvas.drawRect(Display.rect(0,0, Display.WIDTH,Display.OFFSET_SCREEN_Y), Color.BLACK);
        canvas.drawRect(Display.rect(0,Display.HEIGHT - Display.OFFSET_SCREEN_Y,Display.WIDTH, Display.OFFSET_SCREEN_Y), Color.BLACK);
        renderControls(canvas);
    }
    public void addGraphic(Graphic g) {
        if (g instanceof Block.Climbable) {
            specialBlocks.climbables.add((Block.Climbable)g);
        }else if (g instanceof Block.Warpzone) {
            specialBlocks.warpzones.add(((Block.Warpzone)g));
        }

        graphics.add(g);
    }
    public class SpecialBlocks {
        public ArrayList<Block.Climbable> climbables = new ArrayList<>();
        public ArrayList<int[]> waters = new ArrayList<>();
        public ArrayList<Block.Warpzone> warpzones = new ArrayList<>();
    }
}
