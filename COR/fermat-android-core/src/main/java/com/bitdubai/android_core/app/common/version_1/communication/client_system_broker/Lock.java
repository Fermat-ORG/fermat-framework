package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by mati on 2016.04.07..
 */
public class Lock {

    private AtomicBoolean isBlock;

    public Lock() {
        this.isBlock = new AtomicBoolean(false);
    }

    public boolean getIsBlock() {
        return isBlock.get();
    }

    public void block() {
        this.isBlock.compareAndSet(false, true);
    }

    public void unblock() {
        this.isBlock.compareAndSet(true, false);
    }
}
