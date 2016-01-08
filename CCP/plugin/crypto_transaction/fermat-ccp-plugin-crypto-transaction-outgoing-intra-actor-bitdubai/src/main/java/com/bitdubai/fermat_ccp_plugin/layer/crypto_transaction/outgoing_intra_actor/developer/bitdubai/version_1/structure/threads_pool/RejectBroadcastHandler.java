package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.threads_pool;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by mati on 2016.01.03..
 */
public interface RejectBroadcastHandler {

    public void rejectedBroadcastExecution(Runnable r, ThreadPoolExecutor executor);
}
