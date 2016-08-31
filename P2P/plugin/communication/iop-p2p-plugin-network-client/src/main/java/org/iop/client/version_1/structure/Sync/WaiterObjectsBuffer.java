package org.iop.client.version_1.structure.Sync;

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
