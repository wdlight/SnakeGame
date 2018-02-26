package com.wide.game.Snake;

import com.wide.game.framework.Game;
import com.wide.game.framework.Graphics;
import com.wide.game.framework.Input;
import com.wide.game.framework.Pixmap;
import com.wide.game.framework.Screen;

import java.util.List;

import static com.wide.game.Snake.Assets.buttons;

/**
 * Created by byungjoopark on 1/23/18.
 */

public class HighScoreScreen extends Screen {

    String highscore[] = new String[5];

    public HighScoreScreen ( Game game){
        super(game);

        for ( int i = 0 ; i < 5 ; i ++){
            highscore[i]= "" + ( i + 1 ) + "." + Settings.highscores[i];
        }



    }


    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size(); for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y > 416) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                        game.setScreen(new MainMenuScreen(game));
                return; }
            } }
    }

    @Override
    public void present(float deltaTime) {

        Graphics g = game.getGraphics();
        Pixmap background = Assets.background;
        Pixmap mainMenu = Assets.mainMenu;

        g.drawPixmap( background, 0,0);
        g.drawPixmap( mainMenu, 64,20, 0,42, 192,42);

        //draw highscore.
        int y = 100;
        for ( int i = 0 ; i < highscore.length ; i ++ ){
            drawText( g, highscore[i] , 20, y + 40*i );
        }

        // Previous Button
        g.drawPixmap( Assets.buttons, 0, 416, 64,64,64,64 );

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

    public void drawText( Graphics g, String line, int x, int y ){


        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
            if (character == ' ') { x += 20;
                continue; }
            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10; } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }
            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }
}
