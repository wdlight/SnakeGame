package com.wide.game.Snake;

import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.wide.game.framework.Game;
import com.wide.game.framework.Graphics;
import com.wide.game.framework.Input;
import com.wide.game.framework.Pixmap;
import com.wide.game.framework.Screen;

import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by byungjoopark on 1/22/18.
 */

public class GameScreen extends Screen {

    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    GameState   state = GameState.Ready;
    World       world;
    int         oldScore = 0;
    String      score ="0";


    // Controller position.
    static final int CONTROL_LEFT_X = 64;
    static final int CONTROL_LEFT_Y = 416;
    static final int CONTROL_RIGHT_X = 256;
    static final int CONTROL_RIGHT_Y = 416;


    static final Rect pauseButtonRect = new Rect ( 0,0, 64,64);
    static final Rect resumeRect = new Rect (80, 100, 240, 148 );
    static final Rect quitRect = new Rect ( 80, 150, 240, 196 );
    static final Rect GameEndXButtonRect = new Rect ( 128,128+64, 200, 200+64);


    private boolean eventInBound ( Input.TouchEvent e, Rect r){
        return ( e.x > r.left && e.x < r.right && e.y> r.top && e.y < r.bottom );
    }

    public GameScreen(Game game)
    {
        super(game);
        world = new World();
    }


    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if ( state == GameState.Ready)
            updateReady( touchEvents);
        else if ( state == GameState.Running)
            updateRunning ( touchEvents, deltaTime);
        else if ( state == GameState.GameOver )
            updateGameOver ( touchEvents );
        else if ( state == GameState.Paused)
            updatePaused ( touchEvents );

    }

    private void updateReady(List<Input.TouchEvent> events){
        if ( events.size() > 0 )
            state = GameState.Running;

    }

    private void updateGameOver ( List<Input.TouchEvent> events){

        for (int i = 0 ; i < events.size(); i ++){

            Input.TouchEvent e = events.get(i);
            if ( e.type == Input.TouchEvent.TOUCH_UP )
            {
                // check inbound of "close" button
                if ( eventInBound( e, GameEndXButtonRect) ){

                    if (Settings.soundEnabled ) Assets.click.play(1);
                    //return to Main Menu.
                    game.setScreen( new MainMenuScreen(game));

                }
            }
        }
    }

    private void updatePaused ( List<Input.TouchEvent> events){
        for (int i = 0 ; i < events.size(); i ++){

            Input.TouchEvent e = events.get(i);
            if ( e.type == Input.TouchEvent.TOUCH_UP )
            {
                // check inbound of "Resume" button
                if ( eventInBound( e, resumeRect ) ){
                    // close button clicked.

                    if (Settings.soundEnabled ) Assets.click.play(1);
                    //redraw world.
                    state = GameState.Running;

                }
                // Quit
                if ( eventInBound( e, quitRect )){
                    // close button clicked.
                    if (Settings.soundEnabled ) Assets.click.play(1);
                    //redraw world.
                    game.setScreen( new MainMenuScreen(game));
                }
            }
        }
    }


    private void updateRunning( List<Input.TouchEvent> events, float deltaTime){

        for (int i = 0 ; i < events.size(); i ++){

            Input.TouchEvent e = events.get(i);
            if ( e.type == Input.TouchEvent.TOUCH_DOWN )
            {
                if ( e.x > CONTROL_RIGHT_X && e.y > CONTROL_RIGHT_Y)
                    world.snake.turnRight();
                if ( e.x < CONTROL_LEFT_X && e.y > CONTROL_LEFT_Y)
                    world.snake.turnLeft();
                // Pause Button Check.

                if ( eventInBound( e , pauseButtonRect ) ){
                    state = GameState.Paused;
                }
            }
        }

        world.update(deltaTime);
        if ( world.gameOver ) {
            Assets.bitten.play(1);
            state = GameState.GameOver;
        }
        if ( oldScore != world.score) {
            oldScore = world.score;
            score = "" + oldScore;
            Assets.eat.play(1);
        }
    }

    private void drawWorld(World world)
    {
        Graphics g = game.getGraphics();

        //draw Stain
        Stain stain = world.stain;
        Pixmap stainPixmap = null;
        if ( stain.type == Stain.TYPE_1)
            stainPixmap = Assets.stain1;

        switch (stain.type ){
            case Stain.TYPE_1 : stainPixmap = Assets.stain1; break;
            case Stain.TYPE_2 : stainPixmap = Assets.stain2; break;
            case Stain.TYPE_3 : stainPixmap = Assets.stain3; break;
        }


        g.drawPixmap ( stainPixmap, stain.x * 32, stain.y * 32 );

        //draw Snake Head & Body(tail )
        Snake snake = world.snake;
        SnakePart head = snake.parts.get(0);
        Pixmap headPixmap = null;
        switch ( snake.direction ) {
            case Snake.UP : headPixmap = Assets.headUp; break;
            case Snake.DOWN : headPixmap = Assets.headDown; break;
            case Snake.RIGHT : headPixmap = Assets.headRight ; break;
            case Snake.LEFT : headPixmap = Assets.headLeft; break;
        }

        g.drawPixmap( headPixmap, head.x * 32, head.y * 32 );

        SnakePart tail = null;
        Pixmap tailPixmap = Assets.tail;
        for ( int i = 0 ; i < snake.parts.size() -1 ; i ++)
        {
            tail = snake.parts.get(i+1);
            g.drawPixmap ( tailPixmap, tail.x * 32, tail.y *32 );

        }
    }


    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap( Assets.background ,0,0);
        drawWorld(world);

        if ( state == GameState.Ready){
            drawReadyUI();
        }
        if ( state == GameState.GameOver){
            drawGameOverUI();
        }
        if (state == GameState.Running){
            drawRunningUI();
        }
        if (state == GameState.Paused){
            drawPauseUI();
        }

        drawText ( g, score, g.getWidth() / 2 - score.length()*20 /2, g.getHeight() -42 );

    }

    public void drawText(Graphics g, String line, int x, int y )
    {
        // font.
        int len = line.length();
        int no = 0;
        char c ;
        Pixmap cpixmap = null;
        int srcWidth = 20, srcX = 0;


        for ( int i = 0 ; i < len ; i ++ )
        {
            c = line.charAt(i);
            no = c - '0';

            Log.d ( "Score", "Score String" + c);
            cpixmap = Assets.numbers;
            g.drawPixmap( cpixmap, x + i*20 ,y, no* 20  , 0, srcWidth , 32 );

            x += srcWidth;
        }

    }

    public void drawReadyUI()
    {
        Graphics g = game.getGraphics();
        g.drawPixmap( Assets.ready, 47,100);
        g.drawLine( 0, 416, 480, 416, Color.BLACK);

    }

    public void drawGameOverUI(){
        Graphics g = game.getGraphics();

        g.drawPixmap( Assets.gameOver, 62,100 );
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);

    }

    public void drawRunningUI(){
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);

    }

    public void drawPauseUI(){
        Graphics g = game.getGraphics();

        g.drawPixmap ( Assets.pause, 80,100);
    }


    @Override
    public void pause() {
        if ( state == GameState.Running )
            state = GameState.Paused;

        if ( world.gameOver) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
