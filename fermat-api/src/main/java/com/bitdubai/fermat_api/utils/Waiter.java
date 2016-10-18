package com.bitdubai.fermat_api.utils;

/**
 * Created by mati on 09/08/16.
 */
public interface Waiter {


    boolean isLocked();

    void lock();

    void unlock();
}
