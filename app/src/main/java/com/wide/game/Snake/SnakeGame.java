package com.wide.game.Snake;

import com.wide.game.framework.AndroidGame;
import com.wide.game.framework.Screen;

/**
 * Created by byungjoopark on 1/23/18.
 */

public class SnakeGame extends AndroidGame {


    @Override
    public Screen getStartScreen( ){
        return new LoadingScreen(this);
    }
}
