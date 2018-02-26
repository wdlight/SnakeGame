package com.wide.game.framework;

import android.view.View;

import java.util.List;


/**
 * Created by byungjoopark on 1/19/18.
 */




public interface TouchHandler extends View.OnTouchListener{


    public boolean isTouchDown( int pointer);
    public int getTouchX (int pointer);
    public int getTouchY (int pointer);
    public List<Input.TouchEvent> getTouchEvents();
}
