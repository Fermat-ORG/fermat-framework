package com.bitdubai.android_core.app.common.version_1.util.services_helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.ClientBrokerService;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.ClientSystemBrokerServiceAIDL;

import java.lang.ref.WeakReference;

/**
 * Created by Matias Furszyfer on 2016.04.05..
 */
public class ClientSideBrokerServiceHelperAidle {

    private WeakReference<Context> contextWeakReference;

    public ClientSideBrokerServiceHelperAidle(Context contextWeakReference) {
        this.contextWeakReference = new WeakReference<Context>(contextWeakReference);
    }

    /**
     * AppsManagerService
     */
    private boolean clientSideBrokerBoundService;
    private ClientSystemBrokerServiceAIDL clientSystemBrokerService;

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection clientBrokerServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            clientSystemBrokerService = ((ClientSystemBrokerServiceAIDL.LocalBinder) service).getService();
            clientSideBrokerBoundService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            clientSideBrokerBoundService = false;
        }
    };

    public void clientSideBrokerBoundService() {
        if (!clientSideBrokerBoundService) {
            Intent intent = new Intent(contextWeakReference.get(), ClientSystemBrokerServiceAIDL.class);
            //startService(intent);
            contextWeakReference.get().bindService(intent, clientBrokerServiceConnection, Context.BIND_AUTO_CREATE);
        }

    }

    public void unbindClientSideBrokerService() {
        if (clientSideBrokerBoundService) {
            // Detach our existing connection.
            contextWeakReference.get().unbindService(clientBrokerServiceConnection);
            clientSideBrokerBoundService = false;
            Log.d(contextWeakReference.get().getPackageName(), "Unbinding AppManagerService");
        }
    }

    public ClientBrokerService getClientSystemBrokerService() {
        return clientSystemBrokerService;
    }

    public void clear() {
        if (clientSideBrokerBoundService) {
            unbindClientSideBrokerService();
        }
        contextWeakReference.clear();
    }

}
