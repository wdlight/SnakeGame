package com.wide.game.framework;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;

/**
 * Created by byungjoopark on 1/18/18.
 */

public class AAudio implements Audio {

    AssetManager assets;
    SoundPool soundpool;

    public AAudio (Activity activity){
        activity.setVolumeControlStream (AudioManager.STREAM_MUSIC);
        this.assets = activity.getAssets();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            AudioAttributes aa = new AudioAttributes.Builder()
                                    .setContentType( AudioAttributes.CONTENT_TYPE_MUSIC  )
                                    .setUsage(AudioAttributes.USAGE_GAME)
                                    .build();



            soundpool = new SoundPool.Builder()
                    .setMaxStreams(20)
                    .setAudioAttributes( aa)
                    .build();
        } else {
            soundpool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }




    }

    @Override
    public Music newSound(String filename){
        try
        {
            AssetFileDescriptor assetFileDescriptor = assets.openFd(filename);
            int soundID = soundpool.load(assetFileDescriptor, 0);
            return new AMusic( assetFileDescriptor);

        }catch ( IOException e ){
            throw new RuntimeException(" Couldn't Load sound " + filename + "'");
        }
    }


}
