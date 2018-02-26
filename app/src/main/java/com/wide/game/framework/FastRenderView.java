package com.wide.game.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by byungjoopark on 1/19/18.
 */

public class FastRenderView extends SurfaceView implements Runnable {

    AndroidGame game;
    Bitmap fb;

    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;

    public FastRenderView (AndroidGame game, Bitmap bm){
        super (game);
        this.game = game;

        this.fb = bm;
        this.holder = getHolder();

    }

    @Override
    public void run() {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();

        while( running) {
            if ( !holder.getSurface().isValid())
                continue;

            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            game.getCurrentScreen().update(deltaTime );
            game.getCurrentScreen().present(deltaTime);

            Canvas c = holder.lockCanvas();
            c.getClipBounds(dstRect);
            c.drawBitmap( fb, null, dstRect, null);
            holder.unlockCanvasAndPost(c);



        }
    }

    public void resume()
    {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void pause()
    {
        running =false;
        while(true){
            try{
                renderThread.join();

            }catch( InterruptedException e){
                //retry..
            }
        }
    }
}
