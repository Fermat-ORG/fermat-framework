package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.threads_pool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
 
public class RejectedBroadcastExecutionHandler implements RejectedExecutionHandler {


    private final RejectBroadcastHandler rejectBroadcastHandler;

    public RejectedBroadcastExecutionHandler(RejectBroadcastHandler rejectBroadcastHandler) {
        this.rejectBroadcastHandler = rejectBroadcastHandler;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        rejectBroadcastHandler.rejectedBroadcastExecution(r,executor);
    }
}
 