package com.wide.game.framework;

/**
 * Created by byungjoopark on 1/18/18.
 */

public interface Music {
    public void play(float volume);
    public void dispose ( );

    public boolean isPlaying();
    public boolean isLooping();
    public boolean isStopped();
    public void setLooping(boolean isLooping);
    public void setVolume(float vol);
    public void stop();
}
