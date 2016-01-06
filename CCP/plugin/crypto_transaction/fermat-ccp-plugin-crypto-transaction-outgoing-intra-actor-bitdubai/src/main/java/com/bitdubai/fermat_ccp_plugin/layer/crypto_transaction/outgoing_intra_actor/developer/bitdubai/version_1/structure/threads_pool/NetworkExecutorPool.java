package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.threads_pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by mati on 2016.01.03..
 */
public class NetworkExecutorPool extends ThreadPoolExecutor implements RejectBroadcastHandler{

    // list of transactions executing
    private CopyOnWriteArrayList<String> lstHash;


    public NetworkExecutorPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        lstHash = new CopyOnWriteArrayList<>();
    }

    public NetworkExecutorPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        lstHash = new CopyOnWriteArrayList<>();
    }

    public NetworkExecutorPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        lstHash = new CopyOnWriteArrayList<>();
    }

    public NetworkExecutorPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        lstHash = new CopyOnWriteArrayList<>();
    }


    @Override
    public void execute(Runnable command) {
        String hash = ((NetworkBroadcastWorker)command).getTransactionHash();
        if(!lstHash.contains(hash)){
            lstHash.add(hash);
            super.execute(command);
        }

    }

    public void rejectedBroadcastExecution(Runnable r, ThreadPoolExecutor executor) {
        String hash = ((NetworkBroadcastWorker)r).getTransactionHash();
        System.out.println("rejectedBroadcastExecution TRANSACTION HASH: " + hash + " BROADCAST is rejected" + executor);
        if(lstHash.contains(hash)) lstHash.remove(hash);
    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null) {
            String hash = ((NetworkBroadcastWorker)r).getTransactionHash();
            System.out.println("afterExecute TRANSACTION HASH: "+ hash + " BROADCAST is rejected");
            System.out.println(t);
            if(lstHash.contains(hash)) lstHash.remove(hash);
        }
    }


    public void rejectedBroadcastExecution(String transactionHash) {
        System.out.println("rejectedBroadcastExecution TRANSACTION HASH: " + transactionHash + " BROADCAST is rejected");
        if(lstHash.contains(transactionHash)) lstHash.remove(transactionHash);
    }
}
