package com.bitdubai.fermat_api.layer.osa_android.broadcaster;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Matias Furszyfer on 2016.03.14..
 */
public class FermatBundle implements Serializable {

    HashMap<String, Object> extras;

    public FermatBundle() {
        this.extras = new HashMap<>();
    }


    public void put(String key, Serializable serializable) {
        extras.put(key, serializable);
    }

    public void put(String key, Object o) {
        if (o instanceof Serializable) {
            extras.put(key, o);
        } else if (AuxiiliaryWrapper.isWrapperType(o.getClass())) {
            extras.put(key, o);
        } else {
            throw new IllegalArgumentException("Object is not Serializable or primitive type");
        }
    }


    public Serializable getSerializable(String key) throws ClassCastException {
        Object o = extras.get(key);
        if (o == null) return null;
        if (o instanceof Serializable) {
            return (Serializable) o;
        } else {
            throw new ClassCastException(new StringBuilder().append("Need Serializable, found").append(o.getClass()).toString());
        }
    }

    public String getString(String key) throws ClassCastException {
        Object o = extras.get(key);
        if (o == null) return null;
        if (o instanceof String) {
            return (String) o;
        } else {
            throw new ClassCastException(new StringBuilder().append("Need String, found").append(o.getClass()).toString());
        }
    }

    public boolean contains(String key) {
        return extras.containsKey(key);
    }


    public int getInt(String key) throws ClassCastException {
        Object o = extras.get(key);
        if (o == null) return 0;
        if (o instanceof Integer) {
            return (int) o;
        } else {
            throw new ClassCastException(new StringBuilder().append("Need int, found").append(o.getClass()).toString());
        }
    }

    public Object get(String key) throws IllegalAccessException {
        if (!extras.containsKey(key))
            throw new IllegalArgumentException("Key is not loaded in extras");
        return extras.get(key);
    }

    public void remove(String key) {
        extras.remove(key);
    }
}
