package com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.sockets;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.Lock;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl.CommunicationServerService;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.FermatModuleObjectWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Matias Furszyfer on 2016.05.01..
 */
public abstract class LocalSocketSession {

    private static final String TAG = "LocalSocketSession";
    private String pkIdentity;
    private LocalSocket localSocket;
    private Thread runner;
    private AtomicInteger messageSize;

    // object to lock the thread until a message is received
    private final Lock waitMessageLocker;


    public LocalSocketSession(String pkIdentity,LocalSocket localSocket) {
        this.localSocket = localSocket;
        this.pkIdentity = pkIdentity;
        waitMessageLocker = new Lock();
    }

    public void start(){
        messageSize = new AtomicInteger(0);
        runner = new Thread(new SessionRunner());
        runner.start();
    }

    public void stop(){
        if(!runner.isInterrupted()) runner.interrupt();
    }

    public void destroy() throws IOException {
        clear();
    }

    public void clear() throws IOException {
        messageSize = null;
        localSocket.close();
    }


    public abstract void onReceiveMessage(FermatModuleObjectWrapper object);

    public void sendMessage(String requestId,Object object){
        if(! (object instanceof Serializable)) throw new IllegalArgumentException("Object :"+object.getClass().getName()+" is nos Serializable");
        if(localSocket!=null){
            FermatModuleObjectWrapper fermatModuleObjectWrapper = new FermatModuleObjectWrapper((Serializable) object,true,requestId);
            ObjectOutput out = null;
            try {
                out = new ObjectOutputStream(localSocket.getOutputStream());
                out.writeObject(fermatModuleObjectWrapper);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    out.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

        }
    }

    public void addWaitingMessage(String dataId){
        Log.i(TAG,"Message arrive, unlocking wait..");
        messageSize.incrementAndGet();
        waitMessageLocker.unblock();
        synchronized (waitMessageLocker){
            waitMessageLocker.notify();
        }
    }

    public void connect() {
        if(!localSocket.isConnected())
            //todo: sacar ese nombre del path del server
            try {
                localSocket.connect(new LocalSocketAddress(CommunicationServerService.SERVER_NAME));
                localSocket.setReceiveBufferSize(500000);
                localSocket.setSoTimeout(6000);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    private class SessionRunner implements Runnable {

        @Override
        public void run() {
            try {
                if(localSocket!=null) {
                    InputStream inputStream = localSocket.getInputStream();
                    while (true) {
                        while(messageSize.get()!=0){
                            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                            Log.i(TAG, "Cantidad de mensajes a recibir: " + messageSize.get());
                            //byte[] readed = new byte[LocalSocketConfiguration.MESSAGE_SIZE];
                            FermatModuleObjectWrapper object = (FermatModuleObjectWrapper) objectInputStream.readObject();
                            //Acá deberia ver tipo de object porque viene el wrapper y el id a donde va
                            if(object!=null) {
                                onReceiveMessage(object);
                                messageSize.decrementAndGet();
                            }else {
                                TimeUnit.SECONDS.sleep(2);
                            }
                        }
                        if(messageSize.get()==0){
                            Log.i(TAG, "Cleaning Socket");
                            //if(objectInputStream!=null) {
                                //objectInputStream.reset();
                              //  objectInputStream.close();
                               // objectInputStream = null;
                            //}
                            boolean flag = false;
                            while(!flag) {
                                waitMessageLocker.block();
                                synchronized (waitMessageLocker){
                                    Log.i(TAG, "Waiting for message..");
                                    waitMessageLocker.wait();
                                }

                                if(!waitMessageLocker.getIsBlock()){
                                    flag = true;
                                }
                            }
                        }
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}
