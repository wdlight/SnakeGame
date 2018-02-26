package com.wide.game.framework;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by byungjoopark on 1/19/18.
 */

public abstract class AndroidGame extends Activity implements Game {

    FastRenderView renderView;
    Graphics g;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    PowerManager.WakeLock wakeLock;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        int fbBufferWidth = isLandscape ? 480 : 320 ;//Resources.getSystem().getDisplayMetrics().widthPixels;
        int fbBufferHeight = isLandscape ? 320 : 480 ; // Resources.getSystem().getDisplayMetrics().heightPixels;
        Bitmap fb = Bitmap.createBitmap(fbBufferWidth, fbBufferHeight, Bitmap.Config.RGB_565);

        Log.d("wide:game", "screen size : (width , height" + fbBufferWidth + ", " + fbBufferHeight);

        float scaleX = (float) fbBufferWidth / Resources.getSystem().getDisplayMetrics().widthPixels;
        float scaleY = (float) fbBufferHeight / Resources.getSystem().getDisplayMetrics().heightPixels;


        Log.d ( "wide:game", "ScaleX,ScaleY is ( " + scaleX + "," + scaleY );

        renderView = new FastRenderView(this, fb);
        g = new AndroidGraphics( getAssets(), fb);
        audio = new AAudio(this);
        fileIO = new AFileIO( getAssets() );
        input = new AndroidInput(this, renderView, scaleX, scaleY);

        screen = getStartScreen();
        setContentView(renderView);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");


        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WAKE_LOCK},
                        1);




                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();

    }

    @Override
    public void onPause()
    {
        super.onPause();

        wakeLock.release();

        renderView.pause();
        screen.pause();
        if ( isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }
    @Override
    public FileIO getFileIO(){
        return fileIO;
    }


    @Override
    public Graphics getGraphics(){
        return g;
    }

    @Override
    public Audio getAudio(){
        return audio;
    }

    @Override
    public void setScreen(Screen screen){
        if ( screen == null ) throw new IllegalArgumentException ( " screen cannot be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);

        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen(){
        return this.screen;

    }




}

