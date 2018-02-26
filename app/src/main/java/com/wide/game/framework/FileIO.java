package com.wide.game.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by byungjoopark on 1/18/18.
 */

public interface FileIO {
    public InputStream readFile(String fielname) throws IOException;

    public OutputStream writeFile(String fielname) throws IOException;

    public InputStream readAsset(String fielname) throws IOException;
}
