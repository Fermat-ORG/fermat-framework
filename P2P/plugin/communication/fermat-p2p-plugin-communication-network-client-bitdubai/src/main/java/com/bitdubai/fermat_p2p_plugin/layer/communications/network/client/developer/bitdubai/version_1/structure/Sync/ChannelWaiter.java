package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.Sync;

import com.bitdubai.fermat_api.utils.Waiter;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by mati on 09/08/16.
 */
public class ChannelWaiter  implements Waiter{

    private String id;

    private AtomicBoolean flag;


    public ChannelWaiter(String id) {
        this.id = id;
        flag = new AtomicBoolean(false);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean isLocked() {
        return flag.get();
    }

    @Override
    public void lock() {
        this.flag.compareAndSet(false, true);
    }

    @Override
    public void unlock() {
        this.flag.compareAndSet(true, false);
    }
}
