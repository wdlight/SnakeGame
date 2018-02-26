package com.wide.game.framework;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by byungjoopark on 1/19/18.
 */

public class AndroidGraphics implements Graphics {

    AssetManager assets;
    Bitmap fb;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect desRect = new Rect();

    public AndroidGraphics ( AssetManager assets, Bitmap fb ){
        this.assets = assets;
        this.fb = fb;
        this.canvas = new Canvas(fb);
        this.paint = new Paint();

    }




    @Override
    public Pixmap newPixmap(String filename, PixmapFormat format) {
        Bitmap.Config config = null;
        if ( format == PixmapFormat.ARGB4444 )
            config = config.ARGB_4444;
        else if ( format == PixmapFormat.RGB565 )
            config = config.RGB_565;
        else
            config = config.ARGB_8888;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bm = null;

        try {
            in = assets.open(filename);
            bm = BitmapFactory.decodeStream(in);
            if(bm == null)
                throw new RuntimeException( "Couldn't load bitmap from " + filename + "'");
        }catch( IOException e)
        {
            throw new RuntimeException( "Couldn't load bitmap from " + filename + "'");

        }finally{
            if ( in != null)
                try{
                in.close();
                }catch ( IOException e){}

        }

        if ( bm.getConfig() == Bitmap.Config.ARGB_4444 )
            format = PixmapFormat.ARGB4444;
        else if ( bm.getConfig() == Bitmap.Config.RGB_565 )
            format = PixmapFormat.RGB565;
        else
            format = PixmapFormat.ARGB8888;


        return new AndroidPixmap(bm, format);
    }

    @Override
    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    @Override
    public void drawPixel(int x, int y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x,y,paint);
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine( x,y, x2,y2,paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect( new Rect(x,y, x+width, y+height), paint);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {

        srcRect = new Rect( srcX, srcY, srcX+srcWidth,srcY+srcHeight);
        desRect = new Rect ( x,y, x+  srcWidth,y+ srcHeight);
        canvas.drawBitmap(  ((AndroidPixmap)pixmap).bm, srcRect, desRect , null );

    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas.drawBitmap( ((AndroidPixmap) pixmap).bm, x, y, null);
    }

    @Override
    public int getWidth() {
        return fb.getWidth();
    }

    @Override
    public int getHeight() {
        return fb.getHeight();
    }
}
