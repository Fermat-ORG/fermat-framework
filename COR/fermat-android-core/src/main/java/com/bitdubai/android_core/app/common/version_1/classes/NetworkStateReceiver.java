package com.bitdubai.android_core.app.common.version_1.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.01.12..
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    protected List<NetworkStateReceiverListener> listeners;
    protected Boolean connected;

    private static final NetworkStateReceiver instance = new NetworkStateReceiver();

    public static NetworkStateReceiver getInstance() {
        return instance;
    }

    private NetworkStateReceiver() {
        listeners = new ArrayList<NetworkStateReceiverListener>();
        connected = null;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null)
            return;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();

        if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
            Log.i("FERMAT", "CONNECTED");
            connected = true;
        } else if (ni != null && ni.getState() == NetworkInfo.State.DISCONNECTED) {
            Log.i("FERMAT", "DISCONNECTED");
            connected = false;
        } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            connected = false;
        }

        Log.i("FERMAT network type", ni.getTypeName());

        for (String key : intent.getExtras().keySet()) {
            Log.i("FERMAT", key);
        }
        ni = (NetworkInfo) intent.getExtras().get("networkInfo");
        if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
            Log.i("FERMAT", "CONNECTED");
            connected = true;
        } else if (ni != null && ni.getState() == NetworkInfo.State.DISCONNECTED) {
            Log.i("FERMAT", "DISCONNECTED");
            connected = false;
        } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            connected = false;
        }


        notifyStateToAll();
    }

    private void notifyStateToAll() {
        for (NetworkStateReceiverListener listener : listeners)
            notifyState(listener);
    }

    private void notifyState(NetworkStateReceiverListener listener) {
        if (connected == null || listener == null)
            return;

        if (connected == true)
            listener.networkAvailable();
        else
            listener.networkUnavailable();
    }

    public void addListener(NetworkStateReceiverListener l) {
        listeners.add(l);
        notifyState(l);
    }

    public void removeListener(NetworkStateReceiverListener l) {
        listeners.remove(l);
    }

    public interface NetworkStateReceiverListener {
        void networkAvailable();

        void networkUnavailable();
    }
}
