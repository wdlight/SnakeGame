package com.wide.game.Snake;

import android.util.Log;

import com.wide.game.framework.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by byungjoopark on 1/22/18.
 */

public class Settings {


    public static boolean soundEnabled = true;
    public static int[] highscores = new int[]{ 100,80, 50, 20, 10};


    public static void load(FileIO files){

        BufferedReader in = null;
        try{
            in = new BufferedReader( new InputStreamReader(files.readFile(".snake"))) ; // settings saved file..
            soundEnabled = Boolean.parseBoolean( in.readLine());

            for ( int i = 0 ; i < 5 ; i ++ ) {
                String a = new String();
                a = in.readLine();
                if (a != null)
                    highscores[i] = Integer.parseInt(a);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally
        {
            try {
                if ( in != null )
                    in.close();
            }catch (IOException e){

            }
        }
    }


    public static void save(FileIO files){

        BufferedWriter out = null;
        try{
            out = new BufferedWriter( new OutputStreamWriter(files.writeFile(".snake"))) ; // settings saved file..
            out.write ( Boolean.toString(soundEnabled));

            for ( int i = 0 ; i < 5 ; i ++ )
                out.write( Integer.toString( highscores[i])) ;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d ("FILE", "trying to save '.snake' file failed");
        }finally {
            try{
                if ( out != null)
                    out.close();
            }catch (IOException e){

            }
        }
    }

    public static void addScore(int score)
    {
        for ( int i = 0 ; i < 5 ; i ++)
        {
            if ( highscores[i] < score )
            {
                for (int j = 4; j > i ; j -- )
                    highscores[j] = highscores[j-1];

                highscores[i] = score;
                break;

            }
        }
    }
}
