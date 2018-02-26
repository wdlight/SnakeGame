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

public class HelpScreen2 extends Screen{

    public static final int LOCX_NEXTBUTTON = 256;
    public static final int LOCY_NEXTBUTTON = 416;
    public static final int LOCX_BUTTON1 = 64;


    public HelpScreen2( Game game){
        super(game);

    }

    public void update(float deltaTime) {

        List<Input.TouchEvent> events = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = events.size();
        for ( int i = 0 ; i < len ; i ++ ){

            Input.TouchEvent e = events.get(i);

            if ( e.type == Input.TouchEvent.TOUCH_UP ){
                if ( e.x > LOCX_NEXTBUTTON && e.y > LOCY_NEXTBUTTON) {
                    game.setScreen( new HelpScreen3(game) );

                    if ( Settings.soundEnabled ) Assets.click.play(1);

                    return;
                } else if ( e.x < LOCX_BUTTON1 && e.y > LOCY_NEXTBUTTON ){
                    game.setScreen( new HelpScreen(game));
                    if ( Settings.soundEnabled ) Assets.click.play(1);
                }

            }

        }
    }

    @Override
    public void present(float deltaTime) {

        Graphics g = game.getGraphics();
        g.drawPixmap( Assets.background,0,0);
        g.drawPixmap( Assets.help2, 64,100);


        g.drawPixmap( Assets.buttons, 0, 416, 64,64,64,64 );
        g.drawPixmap( Assets.buttons, 256, 416, 0,64,64,64 );


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
