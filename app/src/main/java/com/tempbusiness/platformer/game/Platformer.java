package com.tempbusiness.platformer.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.tempbusiness.platformer.background.Game;
import com.tempbusiness.platformer.background.GameHandler;
import com.tempbusiness.platformer.background.Touchable;
import com.tempbusiness.platformer.game.level.Camera;
import com.tempbusiness.platformer.game.level.Room;
import com.tempbusiness.platformer.graphicobjects.Block;
import com.tempbusiness.platformer.graphicobjects.Button;
import com.tempbusiness.platformer.graphicobjects.Entity;
import com.tempbusiness.platformer.graphicobjects.GameObject;
import com.tempbusiness.platformer.graphicobjects.Graphic;
import com.tempbusiness.platformer.graphicobjects.Player;
import com.tempbusiness.platformer.graphics.Display;
import com.tempbusiness.platformer.graphics.Renderer;
import com.tempbusiness.platformer.graphics.Transition;
import com.tempbusiness.platformer.util.ImageUtil;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;

public class Platformer extends GameHandler {
    public final static int BLOCKS_PER_SCREEN = 20, BUTTON_SIZE = 300;
    public Button left, right, jump, up, down, action;
    public Player player;
    public Room currentRoom;
    public Camera cam;
    public boolean canMove = true;
    public SpecialBlocks specialBlocks;


    public Platformer(Game gameInstance) {
        this.game = gameInstance;
        this.cam = new Camera(this);

       changeRoom(new Room(this, Room.ID.L1_1));
    }

    public void changeRoom(Room r) {
        graphics.clear();
        specialBlocks = new SpecialBlocks();

        this.currentRoom = r;
        this.currentRoom.setup();

        player = new Player(currentRoom.pStartX, currentRoom.pStartY,this);
        graphics.add(player);

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

        if (Display.WIDTH <= 2560) {
            jump = new Button(Color.argb(100,255,0,0), this, Touchable.basic(Display.WIDTH - BUTTON_SIZE - 25, Display.HEIGHT - BUTTON_SIZE - 75, BUTTON_SIZE, BUTTON_SIZE));
        }else{
            jump = new Button(Color.argb(100,255,0,0), this, Touchable.basic(right.x, left.y - BUTTON_SIZE - 75, BUTTON_SIZE, BUTTON_SIZE));
        }

        graphics.add(left);
        graphics.add(right);
        graphics.add(up);
        graphics.add(down);
        graphics.add(jump);
        graphics.add(action);

    }

    public void checkControls() {
        if (player.moveState.equals(Player.WALK)) {
            if (left.area.touching) {
                player.inputAcc = -Player.RUN_ACC / (player.velX > 0 ? 0.4f : 1f);
            } else if (right.area.touching) {
                player.inputAcc = Player.RUN_ACC / (player.velX < 0 ? 0.4f : 1f);
            } else {
                player.inputAcc = 0;
            }
            if (jump.area.touching) {
                if (player.ascendCounter == Player.JUMP_FRAMES) return;

                player.ascendCounter++;
                player.velY = Player.JUMP_SPEED;
            } else {
                if (player.grounded) {
                    player.ascendCounter = 0;
                } else {
                    player.ascendCounter = Player.JUMP_FRAMES;
                }
            }
            if ((up.area.touching || down.area.touching) && up.visible) {
                player.moveState = Player.CLIMB;
                player.stop();
            }
        }else if (player.moveState.equals(Player.CLIMB)) {
            if (left.area.touching) {
                player.velX = -Player.CLIMB_SPEED_X;
            } else if (right.area.touching) {
                player.velX = Player.CLIMB_SPEED_X;
            }else{
                player.velX = 0;
            }

            if (jump.area.touching) {
                player.moveState = Player.WALK;
                player.ascendCounter = 1;
                player.velY = Player.JUMP_SPEED;
            }

            if (up.area.touching) {
                player.velY = Player.CLIMB_SPEED_Y;
            }else if (down.area.touching){
                player.velY = -Player.CLIMB_SPEED_Y;
            }else{
                player.velY = 0;
            }
        }else if (player.moveState.equals(Player.SWIM)) {
            if (left.area.touching) {
                player.inputAcc = -Player.SWIM_ACC / (player.velX > 0 ? 0.4f : 3);
            } else if (right.area.touching) {
                player.inputAcc = Player.SWIM_ACC / (player.velX < 0 ? 0.4f : 3);
            } else {
                player.inputAcc = 0;
            }
            if (jump.area.touching) {
                if (player.ascendCounter == 1) return;

                player.ascendCounter++;

                if (left.area.touching) {
                    player.inputAcc = -Player.SWIM_FORCE;
                }else if (right.area.touching) {
                    player.inputAcc = Player.SWIM_FORCE;
                }
                player.accY = Player.SWIM_ASCEND;
            }else {
                player.ascendCounter = 0;
            }
        }
    }
    public void tick() {
        superTick();

        for (int i = 0; i < graphics.size(); i++) {
            graphics.get(i).tick();
            if (graphics.get(i) instanceof Entity) {
                ((Entity) graphics.get(i)).gravity();
            }
        }

        playerInteractCheck();
        cam.tick();
    }
    public void playerInteractCheck() {
        boolean canClimb = false;
        for (Block.Climbable c : specialBlocks.climbables) {
            final float indent = 0.6f;
            GameObject.Hitbox climbArea = new GameObject.Hitbox(c.x + (c.w * (1-indent)) / 2,c.y + (c.h * (1-indent)) / 2, c.w * indent, c.h * indent);
            if (player.getHitbox().full.intersect(climbArea.full)) {
                canClimb = true;
            }
        }
        up.visible = canClimb;
        down.visible = canClimb;

        if (player.moveState.equals(Player.CLIMB) && !canClimb) {
            player.moveState = Player.WALK;
            player.velY = 0;
        }

        for (int[] water : specialBlocks.waters) {
            final float indent = 0.5f;
            GameObject.Hitbox swimArea = new GameObject.Hitbox(water[0] + (1-indent),water[1] + (1-indent), water[2] - 2 + 2 * indent, water[3] - 2 + 2 * indent);
            if (player.getHitbox().full.intersect(swimArea.full)) {
                player.moveState = Player.SWIM;
            }else if (player.moveState.equals(Player.SWIM)){
                player.moveState = Player.WALK;
            }
        }

        boolean canWarp = false;
        for (final Block.Warpzone w : specialBlocks.warpzones) {
            if (w.room != null && player.getHitbox().full.intersect(w.getHitbox().full)) {
                canWarp = true;

                if (action.area.touching && player.grounded) {
                    game.canvas.currentTransition = new Transition(Renderer.GRenderer.absoluteX(player.x,this),Renderer.GRenderer.absoluteY(player.y,this), Display.displaySize(player.w), Display.displaySize(player.h),30, true);
                    game.canvas.currentTransition.end = new Runnable() {
                        @Override
                        public void run() {
                            changeRoom(w.room);
                            game.canvas.currentTransition = new Transition(Renderer.GRenderer.absoluteX(player.x,Platformer.this),Renderer.GRenderer.absoluteY(player.y,Platformer.this), Display.displaySize(player.w), Display.displaySize(player.h),30, false);
                            game.canvas.currentTransition.startDelay = 30;
                        }
                    };
                    return;
                }
            }
        }

        action.visible = canWarp;
    }
    public void render(Renderer canvas) {
        super.render(canvas);

        canvas.drawRect(ImageUtil.rect(0,0,Display.OFFSET_SCREEN_X, Display.HEIGHT), Color.BLUE);
        canvas.drawRect(ImageUtil.rect(Display.WIDTH - Display.OFFSET_SCREEN_X,0,Display.OFFSET_SCREEN_X, Display.HEIGHT), Color.BLUE);

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
