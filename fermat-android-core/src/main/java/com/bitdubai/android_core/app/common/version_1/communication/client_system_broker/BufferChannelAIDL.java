package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;

import android.util.Log;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by MAtias Furszyfer on 2016.04.27..
 */
public class BufferChannelAIDL {


    private static final String TAG = "BufferChannelAIDL";
//    private ConcurrentMap<String,ByteArrayOutputStream> objects;
    private ConcurrentMap<String,Object> buffer;
    private ConcurrentMap<String,Lock> locks1;


    public BufferChannelAIDL() {
        locks1 = new ConcurrentHashMap<>();
        buffer = new ConcurrentHashMap<>();
    }

    public void addFullDataAndNotificateArrive(String id, Serializable data){
        Log.i(TAG, "Notification object arrived");
        if(data!=null) Log.i(TAG, data.toString());
        if (!locks1.containsKey(id)) {
            buffer.put(id, (data != null) ? data : new EmptyObject());
        }else {
            Lock lock = locks1.get(id);
            if (lock != null) {
                synchronized (lock) {
                    buffer.put(id, (data != null) ? data : new EmptyObject());
                    //locks.get(id).release();
                    lock.unblock();
                    lock.notify();
                }
            } else {
                Log.e(TAG, "lOCK IS NULL,FOR ID:" + id + " DATA ARRIVED: " + ((data != null) ? data.getClass() + " " + data.toString() : "null") + " PLEASE TALK WITH FURSZY .class: " + getClass().getName() + " line:" + new Throwable().getStackTrace()[0].getLineNumber());
            }
        }
    }

    public boolean lockObject(String id) throws InterruptedException {
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
        return true;
    }

    public Object getBufferObject(String id){
        try {
            if (!buffer.containsKey(id)) lockObject(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        Log.i(TAG,"getBufferObject");

        if(buffer.containsKey(id)){
            Log.i(TAG,"getBufferObject returned ok");
            return buffer.get(id);
        }
        return null;

    }



}
