package com.wide.game.framework;

/**
 * Created by byungjoopark on 1/19/18.
 */

public interface Game
{
    public Input getInput();
    public FileIO getFileIO();
    public Graphics getGraphics();
    public Audio getAudio();
    public void setScreen(Screen screen);
    public Screen getCurrentScreen();
    public Screen getStartScreen();

}
