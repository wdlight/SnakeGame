package com.wide.game.framework;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.IOException;

/**
 * Created by byungjoopark on 1/18/18.
 */

public class AMusic implements Music, OnCompletionListener {

    MediaPlayer mediaPlayer;
    boolean isPrepared = false;


    public AMusic(AssetFileDescriptor assetFD){

        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource( assetFD.getFileDescriptor(),
                                        assetFD.getStartOffset(),
                                        assetFD.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        }catch( Exception e){
            throw new RuntimeException("Couldn't play music");
        }

    }



    @Override
    public void play(float volume) {
        if ( mediaPlayer.isPlaying())
            return;

        /*
        try{
            synchronized (this){
                if (!isPrepared){
                    mediaPlayer.reset();
                    mediaPlayer.stop();

                    mediaPlayer.prepare();
                }


                mediaPlayer.start();
            }
        }catch(IllegalStateException ie){
            ie.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        */
        mediaPlayer.start();

    }

    @Override
    public void dispose(){
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();

    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    @Override
    public boolean isStopped() {
        return isPrepared;
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        synchronized (this){
            isPrepared = false;
        }
    }

    @Override
    public void setLooping( boolean isLooping ){
        isLooping = isLooping;
    }

    @Override
    public void setVolume( float vol){
        mediaPlayer.setVolume( vol, vol);
    }

    @Override
    public void stop()
    {
        mediaPlayer.stop();;
        synchronized ( this){
            isPrepared = false;
        }
    }
}
