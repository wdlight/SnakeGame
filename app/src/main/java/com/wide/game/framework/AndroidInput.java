package com.wide.game.framework;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by byungjoopark on 1/19/18.
 */

public class AndroidInput implements Input {

    KeyboardHandler hKeyboard;
    MultiTouchHandler hTouch;

    public AndroidInput (Context ctx, View view, float scaleX, float scaleY){
        hKeyboard = new KeyboardHandler(view);
        hTouch = new MultiTouchHandler(view, scaleX, scaleY);

    }

    @Override
    public boolean isKeyPressed(int keyCode) {

        return hKeyboard.isKeyPressed(keyCode);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return hTouch.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return hTouch.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return hTouch.getTouchY(pointer);
    }

    @Override
    public List<KeyEvent> getKeyEvents() {
        return hKeyboard.getKeyEvents();
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return hTouch.getTouchEvents();
    }
}
