package com.tempbusiness.platformer.game.level;

import android.graphics.Color;

import com.tempbusiness.platformer.game.gameobject.EntityUtil;
import com.tempbusiness.platformer.game.gameobject.Player;
import com.tempbusiness.platformer.game.graphics.Button;
import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;
import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.touch.Touchable;
import com.tempbusiness.platformer.util.Util;

public class Controller {
    public static Button left, right, jump, up, down, action;
    private final float JUMP_VEL = 0.3f, MAX_RUN_SPEED = 0.3f, RUN_ACC = 0.005f;
    private final int MAX_JUMP_FRAMES = 15, RUN_DEC = 2;
    private final int BUTTON_SIZE;
    private Platformer handler;
    private Player player;
    private MoveState moveState;
    private JumpHandler jumpHandler;


    public Controller(Platformer handler) {
        this.handler = handler;
        BUTTON_SIZE = (int) Math.min(Display.dpToPx(100, handler.getContext()), GRenderer.BLOCK_SIZE * 3);
        moveState = MoveState.NONE;
        jumpHandler = new JumpHandler();
    }

    public void setupButtons() {
        final float indent = Display.dpToPx(10, handler.getContext());
        left = new Button(Color.argb(100,255,0,0), handler, Touchable.basic(0, Display.HEIGHT - BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE));
        right = new Button(Color.argb(100,255,0,0),handler, Touchable.basic(left.x + left.w + indent, left.y, BUTTON_SIZE, BUTTON_SIZE) );
        jump = new Button(Color.argb(100,255,0,0), handler, Touchable.basic(Display.WIDTH - BUTTON_SIZE, left.y, BUTTON_SIZE, BUTTON_SIZE));
        down = new Button(Color.argb(100,255,0,0), handler,Touchable.basic(jump.x, jump.y - BUTTON_SIZE - indent, BUTTON_SIZE, BUTTON_SIZE));
        down.visible = false;
        up = new Button(Color.argb(100,255,0,0), handler,Touchable.basic(jump.x, down.y - BUTTON_SIZE - indent, BUTTON_SIZE, BUTTON_SIZE));
        up.visible = false;
        action = new Button(Color.argb(100,255,0,0), handler,Touchable.basic(down.x, down.y, BUTTON_SIZE, BUTTON_SIZE));
        action.visible = false;

        handler.addExternalGraphic(left);
        handler.addExternalGraphic(right);
        handler.addExternalGraphic(jump);
        handler.addExternalGraphic(down);
        handler.addExternalGraphic(up);
        handler.addExternalGraphic(action);
    }

    public void update(Player player) {
        this.player = player;
    }

    public void setMoveState(MoveState m) {
        this.moveState = m;
    }

    public enum MoveState {
        WALK, NONE
    }

    public void checkControls() {
        if (moveState == MoveState.WALK) handleWalk();
    }

    private void handleWalk() {
        if (left.isInTouch()) {
            EntityUtil.accelerateXTo(player, -MAX_RUN_SPEED, -RUN_ACC);
        }else if (right.isInTouch()) {
            EntityUtil.accelerateXTo(player, MAX_RUN_SPEED, RUN_ACC);
        }else{
            EntityUtil.accelerateXTo(player, 0, Math.signum(player.velX) * -RUN_ACC/RUN_DEC);
        }

        jumpHandler.handle(jump.isInTouch());
    }

    private class JumpHandler {
        private float power;
        private int jumpCounter;

        private void handle(boolean jumpButton) {
            if (jumpButton) {
                if (player.isOnCeiling()) jumpCounter = MAX_JUMP_FRAMES;

                if (jumpCounter < MAX_JUMP_FRAMES) {
                    if (jumpCounter == 0) {
                        power = JUMP_VEL * (1 + player.speedX() / 2);
                    }

                    player.velY = power;
                    jumpCounter++;
                }
            }else{
                if (player.isGrounded()) jumpCounter = 0;
                else jumpCounter = MAX_JUMP_FRAMES;
            }
        }
    }
}
