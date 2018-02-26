package com.wide.game.Snake;

import android.util.Log;

import java.util.Random;

/**
 * Created by byungjoopark on 1/22/18.
 */

public class World
{
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 13;
    static final int SCORE_INCREMENT = 10;
    static final float TICK_INITIAL = 0.5f;
    static final float TICK_DECREMENT = 0.05f;

    public Snake snake;
    public Stain stain;
    boolean field[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];


    public boolean gameOver = false;
    float tickTime = 0;
    static float tick = TICK_INITIAL;
    int score = 0;

    Random random = new Random();

    public World() {
        snake = new Snake();
        placeStain();
    }

    public void placeStain(){
        //random place stain.
        for ( int x = 0 ; x < WORLD_WIDTH ; x++)
            for (int y = 0 ; y < WORLD_HEIGHT ; y++)
                field[x][y] = false;

        int len = snake.parts.size();
        for (int i = 0 ; i < len ; i ++)
        {
            SnakePart part = snake.parts.get(i);
            field[part.x][part.y] = true;

        }

        int stainX = random.nextInt( WORLD_WIDTH);
        int stainY = random.nextInt( WORLD_HEIGHT);

        // if the field is occupied.
        if ( field[stainX][stainY] )
        {
            while(field[stainX][stainY] == false){
                stainX = ( stainX +1 ) % WORLD_WIDTH;
                stainY = ( stainY +1 ) % WORLD_HEIGHT;
            }
        }
        stain = new Stain(stainX, stainY, random.nextInt(3)% 3 + 1);

    }


    public void update(float deltaTick){

        if (gameOver) return;

        tickTime += deltaTick;

        while ( tickTime > tick ){
            tickTime -= tick ; // every 0.5 second move.. ( tick will dimished after score is over every 100 points.

            // snake advance
            SnakePart head = snake.advance();    // snake position update ; calc also the size of WORLD and check if it bite itself, eat stain (Game Over )

            // check points. ( running / game over / eat )
            if ( snake.bitten() ) {
                gameOver = true;
                return; // game over.
            }

            else if ( head.x == stain.x && head.y == stain.y )
            {
                // score increment
                score += SCORE_INCREMENT;
                // new stain creation.
                placeStain();

                // game level up.
                if ( score % 50 == 0 ) {
                    tick -= TICK_DECREMENT;
                    snake.addTail = true;

                }
            }
        }
    }
}
