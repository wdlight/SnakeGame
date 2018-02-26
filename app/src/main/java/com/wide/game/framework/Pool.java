package com.wide.game.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byungjoopark on 1/19/18.
 */

public class Pool<T> {
    public interface PoolObjectFactory<T>{
        public T createObject();
    }

    private  List<T> freeObjects = null;
    private  PoolObjectFactory<T> factory = null;
    private  int maxsize = 0;

    public Pool( PoolObjectFactory<T> factory, int maxsize){
        this.factory = factory;
        this.maxsize = maxsize;
        this.freeObjects = new ArrayList<T>(maxsize);

    }

    public T newObject(){
        T object = null;

        if ( freeObjects.size() == 0)
            object = factory.createObject();
        else
            object = freeObjects.remove( freeObjects.size() -1 );

        return object;
    }

    public void free(T object)
    {
        if (freeObjects.size() < maxsize)
            freeObjects.add (object);
    }
}
