package com.bitdubai.android_core.app.common.version_1.util.services_helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.CommunicationMessages;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.CommunicationService;

import java.lang.ref.WeakReference;

/**
 * Created by Matias Furszyfer on 2016.04.05..
 * TODO: este service en realidad ya no se usa más, el que sirve es el clientBroker
 */
public class CommunicationServiceHelper {

    private static final String TAG = "CommServiceHelper";

    private WeakReference<Context> contextWeakReference;
    private final Messenger mMessenger = new Messenger(new IncomingHandler());
    boolean mBound;

    /**
     * Service
     */
    private static Messenger mServiceMcu = null;
    private boolean mCommunicationServiceConnected;

    public CommunicationServiceHelper(Context contextWeakReference) {
        this.contextWeakReference = new WeakReference<Context>(contextWeakReference);
    }

    private ServiceConnection mServiceCommunicationConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCommunicationServiceConnected = false;
            mServiceMcu = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            mServiceMcu = new Messenger(service);
            Log.d(TAG, "Attached.");

            // We want to monitor the service for as long as we are
            // connected to it.
            try {
                Message msg = Message.obtain(null,
                        CommunicationMessages.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mServiceMcu.send(msg);

            } catch (RemoteException e) {
                // In this case the service has crashed before we could even
                // do anything with it; we can count on soon being
                // disconnected (and then reconnected if it can be restarted)
                // so there is no need to do anything here.
                Log.e(TAG, "FermatService is not running");
            }



            //communicationService = ((CommunicationService.LocalBinder)service).getService();
            mCommunicationServiceConnected = true;
        }
    };

    /**
     * Handler of incoming messages from service.
     * todo: esto podria sacarlo y armar una clase que controle las recepciones más elegante
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunicationMessages.MSG_REQUEST_DATA_MESSAGE:
                    Log.d(TAG, "Received from service: " + msg.arg1);
                    //String keyToResponse = msg.getData().getString(DATA_KEY_TO_RESPONSE);
                    //onMessageRecieve(msg.getData().getSerializable(keyToResponse));
                    break;
                case CommunicationMessages.MSG_REGISTER_CLIENT:
                    Log.d(TAG, "Received from service: " + msg.arg1);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    void bindCommService() {
        try{
            Log.d(TAG, "Before init intent.componentName");

            Intent intentForMcuService = new Intent();
            Log.d(TAG,"Package: "+CommunicationService.class.getPackage().getName());
            Log.d(TAG,"class cannonical: "+CommunicationService.class.getCanonicalName());
            Log.d(TAG, "class name: " + CommunicationService.class.getName());
            Log.d(TAG, "class simple name: " + CommunicationService.class.getSimpleName());
            //intentForMcuService.setClassName(CommunicationService.class.getPackage().getName(), "com.bitdubai.android_core.app.common.version_1.communication.CommunicationService");

            intentForMcuService.setComponent(new ComponentName(CommunicationService.class.getPackage().getName(), CommunicationService.class.getName()));
            Log.d(TAG, "Before bindService");

            if (contextWeakReference.get().bindService(intentForMcuService, mServiceCommunicationConnection, Context.BIND_AUTO_CREATE)){
                Log.d(TAG, "Binding to Modem Watcher returned true");
            } else {
                Log.d(TAG, "Binding to Modem Watcher returned false");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "can't bind to ModemWatcherService, check permission in Manifest");
        }
    }

    void unbindCommService() {
        if (mCommunicationServiceConnected) {
            // If we have received the service, and hence registered with
            // it, then now is the time to unregister.
            if (mServiceMcu != null) {
//                try {
//                    Message msg = Message.obtain(null, MSG_UNREGISTER_CLIENT);
//                    msg.replyTo = mMessenger;
//                    mServiceMcu.send(msg);
//                } catch (RemoteException e) {
//                    // There is nothing special we need to do if the service
//                    // has crashed.
//                }
            }

            // Detach our existing connection.
            contextWeakReference.get().unbindService(mServiceCommunicationConnection);
            mCommunicationServiceConnected = false;
            Log.d(TAG, "Unbinding.");
        }
    }

    public void clear(){
        if(mCommunicationServiceConnected){
            contextWeakReference.get().unbindService(mServiceCommunicationConnection);
            mCommunicationServiceConnected = false;
            Log.d(contextWeakReference.get().getPackageName(), "Unbinding CommService");
        }
        contextWeakReference.clear();
    }


}
