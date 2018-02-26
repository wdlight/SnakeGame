package com.wide.game.Snake;

import android.graphics.Color;

import com.wide.game.framework.Game;
import com.wide.game.framework.Graphics;
import com.wide.game.framework.Input;
import com.wide.game.framework.Screen;

import java.util.List;

/**
 * Created by byungjoopark on 1/23/18.
 */

public class HelpScreen extends Screen {

    public static final int LOCX_NEXTBUTTON = 256;
    public static final int LOCY_NEXTBUTTON = 416;

    public HelpScreen( Game game )
    {
        super(game);

    }

    @Override
    public void update(float deltaTime) {

        List<Input.TouchEvent> events = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = events.size();
        for ( int i = 0 ; i < len ; i ++ ){

            Input.TouchEvent e = events.get(i);

            if ( e.type == Input.TouchEvent.TOUCH_UP ){
                if ( e.x > LOCX_NEXTBUTTON && e.y > LOCY_NEXTBUTTON) {
                    game.setScreen( new HelpScreen2(game) );

                    if ( Settings.soundEnabled ) Assets.click.play(1);

                    return;
                }
                else if ( e.x < 64 && e.y > LOCY_NEXTBUTTON){
                    game.setScreen( new MainMenuScreen(game));

                    return;
                }

            }

        }
    }

    @Override
    public void present(float deltaTime) {

        Graphics g = game.getGraphics();
        g.drawPixmap( Assets.background,0,0);
        g.drawPixmap( Assets.help1, 64,100);


        g.drawPixmap( Assets.buttons, 0, LOCY_NEXTBUTTON, 64,64,64,64 );
        g.drawPixmap( Assets.buttons, LOCX_NEXTBUTTON, LOCY_NEXTBUTTON, 0,64,64,64 );





    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
