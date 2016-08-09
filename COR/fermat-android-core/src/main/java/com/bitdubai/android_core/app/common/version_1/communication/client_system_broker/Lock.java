package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;


import com.bitdubai.fermat_api.utils.Waiter;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by mati on 2016.04.07..
 */
public class Lock implements Waiter {

    private AtomicBoolean isBlock;

    public Lock() {
        this.isBlock = new AtomicBoolean(false);
    }

    @Override
    public boolean isLocked() {
        return isBlock.get();
    }

    @Override
    public void lock() {
        this.isBlock.compareAndSet(false, true);
    }

    @Override
    public void unlock() {
        this.isBlock.compareAndSet(true, false);
    }
}
