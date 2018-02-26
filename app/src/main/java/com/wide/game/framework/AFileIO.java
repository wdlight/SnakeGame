package com.wide.game.framework;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;


/**
 * Created by byungjoopark on 1/18/18.
 */

public class AFileIO implements FileIO {
    AssetManager assets;
    String externalStoragePath;

    public AFileIO ( AssetManager assets)
    {
        this.assets = assets;
        this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

        Log.d ( "snake", "filestorage pagth : " + externalStoragePath );

    }

    @Override
    public InputStream readAsset(String filename) throws IOException {
        return assets.open(filename);
    }

    @Override
    public InputStream readFile(String filename) throws IOException {

        return new FileInputStream( externalStoragePath + filename);
    }

    @Override
    public OutputStream writeFile(String filename) throws IOException {
        return new FileOutputStream ( externalStoragePath + filename);
    }
}
