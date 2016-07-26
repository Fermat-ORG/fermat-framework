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


    public LocalSocketSession(String pkIdentity, LocalSocket localSocket) {
        this.localSocket = localSocket;
        this.pkIdentity = pkIdentity;
    }

    public void startReceiving() {
        if (objectInputStream != null) throw new RuntimeException("InvalidState");
        if (runner != null) runner.interrupt();
        try {
            if (objectInputStream == null)
                objectInputStream = new ObjectInputStream(localSocket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        runner = new Thread(new SessionRunner());
        runner.start();
    }

    public void startSender() {
        if (objectOutputStream == null) {
            try {
                objectOutputStream = new ObjectOutputStream(localSocket.getOutputStream());
                isSenderActive = true;
                objectOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void stop() {
        if (!runner.isInterrupted()) runner.interrupt();
    }

    public void destroy() throws IOException {
        stopReceiver();
        stopSender();
        clear();
    }

    public void clear() throws IOException {
        try {
            localSocket.close();
        } catch (Exception e) {

        }

    }

    public void stopSender() {
        try {
            if (isSenderActive) {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopReceiver() {
        if (isReceiverActive) {
            try {
                runner.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public abstract void onReceiveMessage(FermatModuleObjectWrapper object);

    ObjectOutputStream objectOutputStream;

    public void sendMessage(String requestId, Object object) {
        if (!(object instanceof Serializable))
            throw new IllegalArgumentException(new StringBuilder().append("Object :").append(object.getClass().getName()).append(" is nos Serializable").toString());
        if (localSocket != null) {
            FermatModuleObjectWrapper fermatModuleObjectWrapper = new FermatModuleObjectWrapper((Serializable) object, true, requestId);
//            ObjectOutput out = null;
            try {
//                objectOutputStream.flush();
//                objectOutputStream.write(1);
                objectOutputStream.writeObject(fermatModuleObjectWrapper);
            } catch (IOException e) {
                if (!localSocket.isConnected()) {
                    if (!localSocket.isOutputShutdown()) {
                        try {
                            localSocket.getOutputStream().flush();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    //test
                    try {
                        reconnect();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                e.printStackTrace();
            } catch (IllegalBlockingModeException e) {
                e.printStackTrace();
            } catch (Exception e) {
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

    public void addWaitingMessage(String dataId) {
        Log.i(TAG, "Message arrive, unlocking wait..");
//        messageSize.incrementAndGet();
//        waitMessageLocker.unblock();
//        synchronized (waitMessageLocker){
//            waitMessageLocker.notify();
//        }
    }

    public void connect() {
        if (!localSocket.isConnected())
            //todo: sacar ese nombre del path del server
            try {
                localSocket.connect(new LocalSocketAddress(PlatformService.SERVER_NAME));
                localSocket.setReceiveBufferSize(500000);
                localSocket.setSoTimeout(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public boolean isSenderActive() {
        return isSenderActive;
    }


    ObjectInputStream objectInputStream;

    public boolean isConnected() {
        return localSocket.isConnected();
    }

    private class SessionRunner implements Runnable {

        @Override
        public void run() {
            try {
                if (localSocket != null) {
                    isReceiverActive = true;
                    int read = -1;
                    while (isReceiverActive) {
                        if (objectInputStream.available() != 0) {
                            read = +objectInputStream.read();
                        } else {
                            read = 0;
                            if (read != -1) {
                                Log.i(TAG, "pidiendo objeto");
                                FermatModuleObjectWrapper object = null;
                                try {
                                    if (localSocket.isConnected()) {
                                        Object o = objectInputStream.readObject();
                                        if (o instanceof FermatModuleObjectWrapper) {
                                            object = (FermatModuleObjectWrapper) o;
                                        } else {
                                            Log.e(TAG, new StringBuilder().append("ERROR, object returned is not FermatModuleObjectWrapper. Object: ").append(o.getClass().getName()).toString());
                                        }
                                    } else Log.e(TAG, "Socket cerrado, hace falta cerrar hilo");
                                } catch (OptionalDataException e) {
                                    e.printStackTrace();
                                    read = +objectInputStream.read();
                                    Log.e(TAG, String.valueOf(read));
                                }
                                //Acá deberia ver tipo de object porque viene el wrapper y el id a donde va
                                if (object != null) {
                                    onReceiveMessage(object);
                                    read--;
                                    //messageSize.decrementAndGet();
                                } else {
                                    Log.e(TAG, "Object receiver null");
                                    Log.e(TAG, new StringBuilder().append("Read: ").append(read).toString());
//                                        TimeUnit.SECONDS.sleep(2);
                                }
                            } else {
                                //Log.e(TAG,"end of input stream");
//                                    isReceiverActive = false;
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    reconnect();
                    startReceiving();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void reconnect() throws IOException {
        destroy();
        connect();
    }


}
