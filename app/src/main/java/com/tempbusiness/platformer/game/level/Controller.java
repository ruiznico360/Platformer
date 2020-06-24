package com.tempbusiness.platformer.game.level;

import android.graphics.Color;

import com.tempbusiness.platformer.game.gameobject.EntityUtil;
import com.tempbusiness.platformer.game.gameobject.player.Player;
import com.tempbusiness.platformer.game.gameobject.player.PlayerWalkPhysics;
import com.tempbusiness.platformer.game.graphics.Button;
import com.tempbusiness.platformer.game.graphics.Display;
import com.tempbusiness.platformer.game.graphics.rendering.GRenderer;
import com.tempbusiness.platformer.game.handler.Platformer;
import com.tempbusiness.platformer.game.touch.Touchable;

public class Controller {
    public static Button left, right, jump, up, down, action;
    public static final boolean DEBUG = false;
    private final int BUTTON_SIZE;
    private Platformer handler;
    private Player player;
    private PlayerWalkPhysics playerWalkPhysics;
    private MoveState moveState;


    public Controller(Platformer handler) {
        this.handler = handler;
        BUTTON_SIZE = (int) Math.min(Display.dpToPx(100, handler.getContext()), GRenderer.BLOCK_SIZE * 3);
        moveState = MoveState.NONE;

        playerWalkPhysics = new PlayerWalkPhysics();
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
        playerWalkPhysics.setPlayer(player);
    }

    public void setMoveState(MoveState m) {
        this.moveState = m;
    }

    public enum MoveState {
        WALK, NONE
    }

    public void checkControls() {
        if (moveState == MoveState.WALK) {
            if (DEBUG) debug();
            else playerWalkPhysics.handleWalk(left.isInTouch(), right.isInTouch(), jump.isInTouch());
        }
    }

    private void debug() {
        final float speed = 0.2f;
        if (left.isInTouch()) player.velX = -speed;
        else if (right.isInTouch()) player.velX = speed;
        else player.velX = 0;

        if (down.isInTouch()) player.velY = -speed;
        else if (jump.isInTouch()) player.velY = speed;
        else player.velY = 0;
    }
}
