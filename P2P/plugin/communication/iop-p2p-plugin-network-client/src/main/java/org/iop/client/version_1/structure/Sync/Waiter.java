package org.iop.client.version_1.structure.Sync;

/**
 * Created by mati on 09/08/16.
 */
public interface Waiter {


    boolean isLocked();

    void lock();

    void unlock();
}
