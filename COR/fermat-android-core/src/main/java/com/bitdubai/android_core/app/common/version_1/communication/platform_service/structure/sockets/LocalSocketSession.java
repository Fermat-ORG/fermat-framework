package com.bitdubai.android_core.app.common.version_1.communication.platform_service.structure.sockets;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.communication.platform_service.aidl.PlatformService;
import com.bitdubai.android_core.app.common.version_1.communication.platform_service.structure.FermatModuleObjectWrapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.nio.channels.IllegalBlockingModeException;

/**
 * Created by Matias Furszyfer on 2016.05.01..
 */
public abstract class LocalSocketSession {

    private static final String TAG = "LocalSocketSession";
    private String pkIdentity;
    private LocalSocket localSocket;
    private Thread runner;

    // object to lock the thread until a message is received
    private boolean isSenderActive;
    private boolean isReceiverActive;

    /**
     * Streams
     */
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private boolean isReConnecting;


    public LocalSocketSession(String pkIdentity,LocalSocket localSocket) {
        this.localSocket = localSocket;
        this.pkIdentity = pkIdentity;
    }

    public void startReceiving(){
        if (objectInputStream!=null) throw new RuntimeException("InvalidState: objectInputStream!=null");
        if (runner!=null) runner.interrupt();
        try{
            if(objectInputStream==null) objectInputStream = new ObjectInputStream(localSocket.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
        runner = new Thread(new SessionRunner());
        runner.start();
    }

    public void startSender(){
        if(objectOutputStream!=null)throw new RuntimeException("InvalidState: objectOutputStream!=null");
            try {
                objectOutputStream = new ObjectOutputStream(localSocket.getOutputStream());
                isSenderActive = true;
                objectOutputStream.flush();
            }catch (Exception e){
                e.printStackTrace();
            }

    }

    private void stopSender(){
        try {
            if (isSenderActive) {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                isSenderActive = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void stopReceiver(){
        if(isReceiverActive){
            try {
                runner.interrupt();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                if(objectInputStream!=null) {
                    objectInputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            isReceiverActive = false;
        }
    }

    public void destroy() throws IOException {
        Log.i(TAG,"destroy method, stopReciever");
        stopReceiver();
        Log.i(TAG, "destroy method, stopSender");
        stopSender();
        Log.i(TAG, "destroy method, clear");
        clear();
    }

    public void clear() throws IOException {
        try {
            localSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    int i = 0;
    public void sendMessage(String requestId,Object object){
        if(! (object instanceof Serializable)) throw new IllegalArgumentException("Object :"+object.getClass().getName()+" is nos Serializable");
        if(localSocket!=null){
            FermatModuleObjectWrapper fermatModuleObjectWrapper = new FermatModuleObjectWrapper((Serializable) object,true,requestId);
            try {
//                objectOutputStream.flush();
//                Log.e(TAG,"LocalSocket states: "+ "connected: "+localSocket.isConnected()+", bound: "+localSocket.isBound());
                Log.i(TAG, "send method: object type return" + object.getClass().getName() + ", number: " + i++);
                sendPackage(fermatModuleObjectWrapper);
            } catch (IOException e) {
                Log.e(TAG,"send IOException");
                if(!localSocket.isConnected()){
                    if (!localSocket.isOutputShutdown()){
                        try {
                            localSocket.getOutputStream().flush();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }else Log.e(TAG, "send method: isOutputShutdown true");
                    //test
                    if (isSenderActive) {
                        try {
                            Log.e(TAG, "send method: reconnecting");
                            reconnect(true, false);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }else Log.e(TAG, "send method: localSocket connected");
                e.printStackTrace();
            }catch (IllegalBlockingModeException e){
                e.printStackTrace();
            } catch (Exception e){
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
    private synchronized void sendPackage(FermatModuleObjectWrapper fermatModuleObjectWrapper) throws IOException {
        synchronized (this) {
            objectOutputStream.write(1);
            objectOutputStream.writeObject(fermatModuleObjectWrapper);
        }
    }


    public void connect() {
        if(localSocket!=null) {
            if (!localSocket.isConnected()) {
                //todo: sacar ese nombre del path del server
                try {
                    Log.i(TAG,"Connect method, connecting localsocket");
                    localSocket.connect(new LocalSocketAddress(PlatformService.SERVER_NAME));
                    localSocket.setReceiveBufferSize(500000);
                    localSocket.setSoTimeout(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else Log.i(TAG,"Connect method, localsocket connected");
        }else Log.e(TAG,"Connect method, localsocket null");
    }

    public synchronized void reconnect(boolean reConnectSender,boolean reConnectReceiver) throws IOException {
        if (!isReConnecting) {
            Log.e(TAG,"Trying to reconnect");
            isReConnecting=true;
            if (localSocket.isConnected()) {
                Log.e(TAG,"destroying localsocket");
                destroy();
            }
            Log.e(TAG,"connecting localsocket");
            connect();
            if (reConnectSender) startSender();
            if (reConnectReceiver) stopReceiver();
            isReConnecting = false;
        }
    }

    public boolean isSenderActive() {
        return isSenderActive;
    }

    public boolean isConnected() {
        return localSocket.isConnected();
    }

    private class SessionRunner implements Runnable {

        @Override
        public void run() {
            try {
                if(localSocket!=null) {
                    isReceiverActive = true;
                    int read = -1;
                        while (isReceiverActive) {
//                                if(objectInputStream.available()!=0) {
                                    read = objectInputStream.read();
//                                }else {
//                                    read = 0;
                                    if (read != -1) {
                                        Log.i(TAG, "pidiendo objeto");
                                        FermatModuleObjectWrapper object = null;
                                        try {
//                                            Log.e(TAG,"LocalSocket states: "+ "connected: "+localSocket.isConnected()+", bound: "+localSocket.isBound());
                                            if(localSocket.isConnected()) {
                                                Object o = objectInputStream.readObject();
                                                if(o instanceof FermatModuleObjectWrapper){
                                                    object = (FermatModuleObjectWrapper) o;
                                                }else{
                                                    Log.e(TAG,"ERROR, object returned is not FermatModuleObjectWrapper. Object: "+o.getClass().getName());
                                                }
                                            }
                                            else Log.e(TAG,"Socket cerrado, hace falta cerrar hilo");
                                        } catch (OptionalDataException e) {
                                            e.printStackTrace();
                                            read = +objectInputStream.read();
                                            Log.e(TAG, String.valueOf(read));
                                        }
                                        //Acá deberia ver tipo de object porque viene el wrapper y el id a donde va
                                        if (object != null) {
                                            onReceiveMessage(object);
                                        } else {
                                            Log.e(TAG, "Object receiver null");
                                            Log.e(TAG, "Read: " + read);
//                                        TimeUnit.SECONDS.sleep(2);
                                        }
                                    } else {
                                        //Log.e(TAG,"end of input stream");
//                                    isReceiverActive = false;
                                    }
//                                }
                        }
                }

            } catch (IOException e) {
                Log.e(TAG,"SessionRunner IOException. Message"+e.getMessage()+". This is only for debug (no jode en absoluto)");
                if(isReceiverActive) {
                    try {
                        reconnect(false, true);
                        startReceiving();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    public abstract void onReceiveMessage(FermatModuleObjectWrapper object);

}
