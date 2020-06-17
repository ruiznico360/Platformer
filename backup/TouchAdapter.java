package com.tempbusiness.platformer.backup;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.HashMap;

public class TouchAdapter {
    public Game game;
    public HashMap<Integer, Coords> activePointers = new HashMap<>();

    public TouchAdapter(Game game) {
        this.game = game;
    }

    public void handleTouchInput(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        int maskedAction = event.getActionMasked();

        if (maskedAction == MotionEvent.ACTION_DOWN || maskedAction == MotionEvent.ACTION_POINTER_DOWN) {
            activePointers.put(pointerId, new Coords(event.getX(pointerIndex), event.getY(pointerIndex)));

            handleDown();
        }else if (maskedAction == MotionEvent.ACTION_MOVE) {
            for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                Coords b = activePointers.get(event.getPointerId(i));
                b.x = event.getX(i);
                b.y = event.getY(i);
            }

            handleDown();
        }else if (maskedAction == MotionEvent.ACTION_UP || maskedAction == MotionEvent.ACTION_POINTER_UP) {
            handleUp(pointerId);
        }else if (maskedAction == MotionEvent.ACTION_CANCEL) {
            for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                handleUp(event.getPointerId(i));
            }
        }
    }
    public void handleDown() {
        for (int i = 0; i < game.handler.touchables.size(); i++) {
            boolean interesct = false;
            Rect touchableBounds = ImageUtil.rect(game.handler.touchables.get(i).x, game.handler.touchables.get(i).y, game.handler.touchables.get(i).w, game.handler.touchables.get(i).h);

            for (Coords c : activePointers.values()) {
                if (touchableBounds.contains((int)c.x, (int)c.y)) {
                    interesct = true;
                }
            }

            if (!game.handler.touchables.get(i).touching && interesct) {
                game.handler.touchables.get(i).down();
            }else if (game.handler.touchables.get(i).touching && !interesct) {
                game.handler.touchables.get(i).up();
            }
        }
    }
    public void handleUp(int pointerId) {
        activePointers.remove(pointerId);
        
        for (int i = 0; i < game.handler.touchables.size(); i++) {
            boolean interesct = false;
            Rect touchableBounds = ImageUtil.rect(game.handler.touchables.get(i).x, game.handler.touchables.get(i).y, game.handler.touchables.get(i).w, game.handler.touchables.get(i).h);

            for (Coords c : activePointers.values()) {
                if (touchableBounds.contains((int)c.x, (int)c.y)) {
                    interesct = true;
                }
            }

            if (game.handler.touchables.get(i).touching && !interesct) {
                game.handler.touchables.get(i).up();
            }
        }
    }
    public class Coords {
        public float x,y;
        public Coords(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
