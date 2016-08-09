package com.bitdubai.fermat_api.utils;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by MAtias Furszyfer on 2016.04.27..
 */
public abstract class BufferObjectWaiter<I extends Waiter> {


    private static final String TAG = "BufferObjectWaiter";
    private ConcurrentMap<String, Object> buffer;
    private ConcurrentMap<String, I> locks1;

    private int requestQuantity = 0;

    public BufferObjectWaiter() {
        locks1 = new ConcurrentHashMap<>();
        buffer = new ConcurrentHashMap<>();
    }



    public void addFullDataAndNotificateArrive(String id, Serializable data) {
        System.out.println(TAG + ": adding object");
        if (data != null) System.out.println(TAG+": "+data.toString());
        if (!locks1.containsKey(id)) {
            buffer.put(id, (data != null) ? data : new Object());
        } else {
            I lock = locks1.get(id);
            if (lock != null) {
                System.out.println(TAG+ ": Arrived Id:" + id + ",Data: " + data);
                synchronized (lock) {
                    buffer.put(id, (data != null) ? data : new Object());
                    lock.unlock();
                    lock.notify();
                }
            } else {
                System.out.println(TAG+": lOCK IS NULL,FOR ID:" + id + " DATA ARRIVED: " + ((data != null) ? String.valueOf(data.getClass()) + " " + data.toString() : "null") + " PLEASE TALK WITH FURSZY .class: " + getClass().getName() + " line:" + new Throwable().getStackTrace()[0].getLineNumber());
            }
        }
    }

    public boolean lockObject(String id) throws InterruptedException {
        if (!buffer.containsKey(id)) {
            System.out.println(TAG+": waiting for object");
            requestQuantity++;
            I lock = buildWaiter(id);
            synchronized (lock) {
                lock.lock();
                locks1.put(id, lock);
                System.out.println(TAG+" :wainting queue quantity: " + locks1.size() + ", total: " + requestQuantity + ",id:" + id);
                while (lock.isLocked()) {
                    lock.wait();
                    System.out.println(TAG + ": thread wake up");
                    System.out.println(TAG +"Lock is: " + lock.isLocked());
                }
            }
            locks1.remove(id);
        }
        return true;
    }

    public Object getBufferObject(String id) {
        try {
            if (!buffer.containsKey(id)) lockObject(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(TAG+": getBufferObject");

        if (buffer.containsKey(id)) {
            System.out.println(TAG+": getBufferObject returned ok");
            return buffer.get(id);
        }
        return null;

    }

    protected abstract I buildWaiter(String uuid) ;

}
