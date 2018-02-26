package com.wide.game.framework;

import android.graphics.Bitmap;



/**
 * Created by byungjoopark on 1/19/18.
 */

public class AndroidPixmap implements Pixmap {

    public Bitmap bm;
    Graphics.PixmapFormat format;

    public AndroidPixmap (Bitmap bm, Graphics.PixmapFormat format)
    {
        this.bm = bm;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bm.getWidth();
    }

    @Override
    public int getHeight() {
        return bm.getHeight();
    }

    @Override
    public Graphics.PixmapFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bm.recycle();

    }
}
