package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.Sync;

import com.bitdubai.fermat_api.utils.BufferObjectWaiter;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.Sync.ChannelWaiter;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 09/08/16.
 */
public class WaiterObjectsBuffer extends BufferObjectWaiter<ChannelWaiter> {

    private static final String TAG = "WaiterObjectsBuffer";


    @Override
    protected ChannelWaiter buildWaiter(String uuid) {
        return new ChannelWaiter(uuid);
    }
}
