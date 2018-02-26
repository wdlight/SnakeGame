package com.wide.game.Snake;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by byungjoopark on 1/22/18.
 */

public class Snake
{

    enum SNAKE_STATE  { FORWARD, EAT, BITE };

    static final int UP = 0 ;
    static final int RIGHT = 1 ;
    static final int DOWN = 2 ;
    static final int LEFT = 3 ;

    static final int[] inc_x = new int[]{0,1,0,-1};
    static final int[] inc_y = new int[]{-1,0,1,0};

    int direction = UP;

    public List<SnakePart> parts = new ArrayList<SnakePart>();

    boolean addTail = false;

    public Snake()
    {
        parts.add( new SnakePart(5,6)); // head
        parts.add( new SnakePart(5,7));
        parts.add( new SnakePart(5,8));

        //Random rand = new Random();
        //direction =rand.nextInt(4);

    }

    //processing the location
    public SnakePart advance(){

        // get last tail and add to the just behind the head.
        SnakePart part = parts.get( parts.size() -1 );

        part.x = parts.get(0).x;
        part.y = parts.get(0).y;
        parts.add(1, part);
        parts.remove(parts.size() - 1);

        if ( addTail ) {
            addTail = false;

            SnakePart newPart = new SnakePart(parts.get(0).x, parts.get(0).y);
            parts.add(0, newPart);
        }

        // head move
        SnakePart head = parts.get(0);
        head.x += inc_x[direction];
        head.y += inc_y[direction];
        //parts.set(0, head);

        if ( head.x > 9   ) head.x = 0;
        else if ( head.x < 0 ) head.x = 9;

        if ( head.y > 12 ) head.y = 0;
        else if ( head.y < 0 ) head.y = 11;

        return head;

    }

    boolean bitten()
    {
   // head move
        SnakePart head = parts.get(0);

        int headx = head.x;
        int heady = head.y;

        SnakePart part = null;

        for ( int i = 1 ; i < parts.size() ; i ++)
        {
            part = parts.get(i);
            int partx = part.x;
            int party = part.y;
            if ( headx == partx && heady == party)
                return true;

        }
        return false;
    }

    public void turnLeft(){

        direction = ( direction + 4 - 1 ) % 4 ;
    }

    public void turnRight(){

        direction = (direction + 1 ) % 4 ;
    }

}
