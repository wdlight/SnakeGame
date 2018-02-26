package com.wide.game.framework;

/**
 * Created by byungjoopark on 1/19/18.
 */

import android.view.*;
import android.view.View.OnKeyListener;

import java.util.ArrayList;
import java.util.List;


public class KeyboardHandler implements OnKeyListener {



    boolean[] pressedKeys = new boolean[128];
    Pool<Input.KeyEvent> keyEventPool ;
    List<Input.KeyEvent> keyEventsBuffer = new ArrayList<Input.KeyEvent>();
    List<Input.KeyEvent> keyEvents = new ArrayList<Input.KeyEvent>();


    public KeyboardHandler( View view){
        Pool.PoolObjectFactory<Input.KeyEvent> factory = new Pool.PoolObjectFactory<Input.KeyEvent>(){
            @Override
            public Input.KeyEvent createObject(){
                return new Input.KeyEvent();
            }
        };
        keyEventPool = new Pool<Input.KeyEvent>(factory,100);
        view.setOnKeyListener ( this );
        view.setFocusableInTouchMode( true );
        view.requestFocus();

    }


    @Override
    public boolean onKey(View v, int keyCode, android.view.KeyEvent event) {

        if ( event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE)
            return false;

        synchronized (this){
            Input.KeyEvent evt = keyEventPool.newObject();
            evt.keyCode = keyCode;
            evt.keyChar = (char) event.getUnicodeChar();

            if ( event.getAction() == android.view.KeyEvent.ACTION_DOWN ){
                evt.type = Input.KeyEvent.KEY_DOWN;
                if ( evt.keyCode >0 && evt.keyCode <127 )
                    pressedKeys[keyCode] = true;

            }else if ( event.getAction() == android.view.KeyEvent.ACTION_UP ){
                evt.type = Input.KeyEvent.KEY_UP;
                if ( evt.keyCode >0 && evt.keyCode <127 )
                    pressedKeys[keyCode] = false;
            }
            keyEventsBuffer.add ( evt );

        }
        return false;
    }

    public boolean isKeyPressed(int keyCode){
        if ( keyCode <0 || keyCode > 127)
            return false;
        return pressedKeys[keyCode];
    }

    public List<Input.KeyEvent> getKeyEvents(){

        synchronized (this){
            int len = keyEvents.size();
            for (int i = 0 ; i < len ; i ++ )
                keyEventPool.free( keyEvents.get(i));
            keyEvents.clear();
            keyEvents.addAll( keyEventsBuffer);
            keyEventsBuffer.clear();
            return keyEvents;
        }

    }
}
