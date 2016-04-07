package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;

import android.util.Log;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

/**
 * Created by Matias Furszyfer on 2016.04.01..
 */
public class BufferChannel {

    private static final String TAG = "BufferChannel";

    private ConcurrentMap<UUID,Object> buffer;
    private ConcurrentMap<UUID,Semaphore> locks;
    private ConcurrentMap<UUID,Lock> locks1;

    public BufferChannel() {
        buffer = new ConcurrentHashMap<>();
        locks = new ConcurrentHashMap<>();
        locks1 = new ConcurrentHashMap<>();
    }

    public Object getObject(UUID id) throws InterruptedException {
        if(!buffer.containsKey(id)){
            Log.i(TAG,"waiting for object");
            //Semaphore semaphore = new Semaphore(1);
            //locks.put(id, semaphore);
            Lock lock = new Lock();
            synchronized (lock){
                lock.block();
                locks1.put(id, lock);
                while(lock.getIsBlock()){
                    lock.wait();
                    Log.i(TAG, "thread wake up");
                    Log.i(TAG, "Lock is: "+lock.getIsBlock());
                }
            }
            //semaphore.acquire();
        }
        Object o = buffer.get(id);
        buffer.remove(id);
        locks1.remove(id);
        return o;
    }

    public void notificateObject(UUID id,Object o){
        Log.i(TAG, "Notification object arrived");
        Log.i(TAG, o.toString());
        Lock lock = locks1.get(id);
        synchronized (lock){
            buffer.put(id, o);
            //locks.get(id).release();
            lock.unblock();
            lock.notify();
        }

    }


}
