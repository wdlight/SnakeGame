package com.wide.game.framework;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byungjoopark on 1/19/18.
 */

public class MultiTouchHandler implements TouchHandler {

    boolean isTouched[] = new boolean[20];
    int[] touchX = new int[20];
    int[] touchY = new int[20];
    Pool<Input.TouchEvent> touchEventPool ;
    List<Input.TouchEvent> touchEvents = new ArrayList<Input.TouchEvent>();
    List<Input.TouchEvent> touchEventsBuffer = new ArrayList<Input.TouchEvent>();
    float scaleX;
    float scaleY;

    public MultiTouchHandler( View view, float scaleX, float scaleY){



        Pool.PoolObjectFactory<Input.TouchEvent> factory = new Pool.PoolObjectFactory<Input.TouchEvent>(){
            @Override
            public Input.TouchEvent createObject()
            {
                return new Input.TouchEvent();
            }
        };
        touchEventPool = new Pool<Input.TouchEvent>( factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;

        Log.d ( "MTE", "MultiTouchHandler Creation w/ " + scaleX + " " + scaleY );

    }


    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this){
            if (pointer < 0 || pointer >20)
                return false;
            else
                return isTouched[pointer];
        }

    }

    @Override
    public int getTouchX(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer > 20)
                return 0;
            else

                return touchX[pointer];
        }
    }

    @Override
    public int getTouchY(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer > 20)
                return 0;
            else

            return touchY[pointer];
        }
    }


    @Override
    public List<Input.TouchEvent> getTouchEvents() {
        synchronized (this){
            int len = touchEvents.size();

            for ( int i = 0 ; i < len ; i ++)
                touchEventPool.free ( touchEvents.get(i));

            touchEvents.clear();
            touchEvents.addAll( touchEventsBuffer );
            touchEventsBuffer.clear();

            return touchEvents;

        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        Log.d ( "MTE", "onTouch event pointer count =" + event.getPointerCount() );


        synchronized (this){

            int a = event.getAction() & MotionEvent.ACTION_MASK;
            int pointer = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK ) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

            Log.d ( "MTE", "pointer value is " + pointer);

            int pointerID = event.getPointerId(pointer);
            Input.TouchEvent ev;

            Log.d ( "MTE", "pointer ID is " + pointerID + "");

            switch(a){
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    ev = touchEventPool.newObject();
                    ev.type = Input.TouchEvent.TOUCH_DOWN;
                    ev.pointer = pointerID;

                    ev.x = touchX[pointerID] = (int ) (event.getX() * scaleX );
                    ev.y = touchY[pointerID] = (int ) (event.getY() * scaleY );

                    isTouched[pointerID] = true;
                    touchEventsBuffer.add( ev);
                    break;


                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP :
                case MotionEvent.ACTION_CANCEL:

                    ev = touchEventPool.newObject();
                    ev.type = Input.TouchEvent.TOUCH_UP;
                    ev.pointer = pointerID;

                    ev.x = touchX[pointerID] = (int ) (event.getX() * scaleX );
                    ev.y = touchY[pointerID] = (int ) (event.getY() * scaleY );

                    isTouched[pointerID] = false;
                    touchEventsBuffer.add( ev);

                    break;

                case MotionEvent.ACTION_MOVE:
                    ev = touchEventPool.newObject();
                    ev.type = Input.TouchEvent.TOUCH_DRAGGED;
                    ev.pointer = pointerID;

                    ev.x = touchX[pointerID] = (int ) (event.getX() * scaleX );
                    ev.y = touchY[pointerID] = (int ) (event.getY() * scaleY );

                    isTouched[pointerID] = false;
                    touchEventsBuffer.add( ev);
                    break;

            }




            return true;
        }



    }
}
