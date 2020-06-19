package com.tempbusiness.platformer.game.touch;

import android.graphics.Rect;
import android.view.MotionEvent;

import com.tempbusiness.platformer.game.Game;
import com.tempbusiness.platformer.game.graphics.Display;

import java.util.HashMap;

public class TouchAdapter {
    private Game game;
    private HashMap<Integer, Coords> activePointers = new HashMap<>();

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
    private void handleDown() {
        for (int i = 0; i < game.getHandler().getTouchables().size(); i++) {
            boolean interesct = false;
            Rect touchableBounds = game.getHandler().getTouchables().get(i).bounds();

            for (Coords c : activePointers.values()) {
                if (touchableBounds.contains((int)c.x, (int)c.y)) {
                    interesct = true;
                }
            }

            if (!game.getHandler().getTouchables().get(i).isInTouch() && interesct) {
                game.getHandler().getTouchables().get(i).down();
            }else if (game.getHandler().getTouchables().get(i).isInTouch() && !interesct) {
                game.getHandler().getTouchables().get(i).up();
            }
        }
    }
    private void handleUp(int pointerId) {
        activePointers.remove(pointerId);
        
        for (int i = 0; i < game.getHandler().getTouchables().size(); i++) {
            boolean interesct = false;
            Rect touchableBounds = game.getHandler().getTouchables().get(i).bounds();

            for (Coords c : activePointers.values()) {
                if (touchableBounds.contains((int)c.x, (int)c.y)) {
                    interesct = true;
                }
            }

            if (game.getHandler().getTouchables().get(i).isInTouch() && !interesct) {
                game.getHandler().getTouchables().get(i).up();
            }
        }
    }
    private class Coords {
        private float x,y;
        private Coords(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
