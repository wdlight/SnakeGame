package com.wide.game.framework;

import android.media.SoundPool;

/**
 * Created by byungjoopark on 1/18/18.
 */

public class ASound implements Sound {

    int soundID;
    SoundPool soundPool;

    public ASound ( int soundID, SoundPool sp){
        this.soundID = soundID;
        this.soundPool = sp;
    }

    @Override
    public void play (float volume){
        soundPool.play( soundID, volume, volume, 0,0,1);
    }

    @Override
    public void dispose(){
        soundPool.unload(soundID);
    }

}
