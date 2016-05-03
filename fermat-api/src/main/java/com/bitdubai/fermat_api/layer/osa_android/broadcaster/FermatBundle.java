package com.bitdubai.fermat_api.layer.osa_android.broadcaster;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by mati on 2016.03.14..
 */
public class FermatBundle {

    HashMap<String,Object> extras;


    public FermatBundle() {
        this.extras = new HashMap<>();
    }


    public void put(String key,Serializable serializable){
        extras.put(key,serializable);
    }


    public Serializable getSerializable(String key) throws IllegalAccessException {
        Object o = extras.get(key);
        if(o instanceof Serializable){
            return (Serializable) o;
        }else{
            throw new IllegalAccessException("Need Serializable, found"+o.getClass());
        }
    }

    public String getString(String key) throws IllegalAccessException {
        Object o = extras.get(key);
        if(o instanceof String){
            return (String) o;
        }else{
            throw new IllegalAccessException("Need String, found"+o.getClass());
        }
    }

    public boolean contains(String key){
        return extras.containsKey(key);
    }


    public int getInt(String key) throws IllegalAccessException {
        Object o = extras.get(key);
        if(o instanceof Integer){
            return (int) o;
        }else{
            throw new IllegalAccessException("Need int, found"+o.getClass());
        }
    }
}
