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
                Log.i(TAG, "wainting queue quantity: " + locks1.size());
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
        if(o instanceof EmptyObject){
            o = null;
        }
        return o;
    }

    public void notificateObject(UUID id,Object o) throws Exception{
        Log.i(TAG, "Notification object arrived");
        if(o!=null) Log.i(TAG, o.toString());
        Lock lock = locks1.get(id);
        synchronized (lock){
            buffer.put(id,(o!=null)? o:new EmptyObject());
            //locks.get(id).release();
            lock.unblock();
            lock.notify();
        }

    }


}
