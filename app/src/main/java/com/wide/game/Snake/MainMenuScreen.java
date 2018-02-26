package com.wide.game.Snake;

import android.util.Log;

import com.wide.game.framework.Game;
import com.wide.game.framework.Graphics;
import com.wide.game.framework.Input;
import com.wide.game.framework.Screen;

import java.util.List;

/**
 * Created by byungjoopark on 1/22/18.
 */

public class MainMenuScreen extends Screen
{

    static final int SIZE_ICON = 64;
    static final int SIZE_MENUHEIGHT = 42;
    //static final int LOCX_SOUND = 64;



    public MainMenuScreen(Game game){
        super(game);

    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<Input.TouchEvent> events = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();


        for ( int i = 0 ; i < events.size() ; i ++){
            Input.TouchEvent e = events.get(i);

            Log.d( "[TE]", "" + e.x + "," + e.y);
            if ( e.type == Input.TouchEvent.TOUCH_UP ) {

                if ( inBounds( e, 0, g.getHeight() -  SIZE_ICON, SIZE_ICON, SIZE_ICON)) // Sount Icon location
                {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if ( Settings.soundEnabled ) Assets.click.play(1);

                }
                // Game Screen
                if ( inBounds( e,   SIZE_ICON, 220, 192, 42  ))
                {
                    game.setScreen( new GameScreen( game));
                    if ( Settings.soundEnabled ) Assets.click.play(1);

                }

                // HighScore Screen
                if ( inBounds( e,   SIZE_ICON, 220 + SIZE_MENUHEIGHT, 192, SIZE_MENUHEIGHT  ))
                {
                    game.setScreen( new HighScoreScreen( game));
                    if ( Settings.soundEnabled ) Assets.click.play(1);

                }


                // Help Screen
                if ( inBounds( e,   SIZE_ICON, 220 + SIZE_MENUHEIGHT*2, 192, SIZE_MENUHEIGHT  ))
                {
                    game.setScreen( new HelpScreen ( game));
                    if ( Settings.soundEnabled ) Assets.click.play(1);

                }

            }
        }


    }


    private boolean inBounds(Input.TouchEvent e, int x, int y, int width, int height) {

        if ( e.x >= x && e.x <= x+width && e.y >= y && e.y <= y+height)
            return true;
        else return false;
    }


    @Override
    public void present(float deltaTime) {

        Graphics g = game.getGraphics();

        g.drawPixmap ( Assets.background, 0,0);
        g.drawPixmap ( Assets.logo, 32,20);

        g.drawPixmap ( Assets.mainMenu, 32*2,220);

        if ( Settings.soundEnabled )
            g.drawPixmap ( Assets.buttons, 0, 416, 0,0, SIZE_ICON, SIZE_ICON);
        else
            g.drawPixmap ( Assets.buttons, 0, 416, SIZE_ICON, 0, SIZE_ICON, SIZE_ICON);


    }

    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
