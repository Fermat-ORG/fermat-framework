package com.bitdubai.android_core.app;

import com.bitdubai.fermat_api.FermatBroadcastReceiver;
import com.bitdubai.fermat_api.FermatIntentFilter;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.07.06..
 */
public class ReceiversManager {

    private HashMap<BroadcasterType, List<FermatBroadcastReceiver>> receivers;

    public ReceiversManager() {
        receivers = new HashMap<>();
    }

    public void registerReceiver(BroadcasterType broadcasterType, FermatBroadcastReceiver fermatBroadcastReceiver, String appPublicKey) {
        List<FermatBroadcastReceiver> list = null;
        if (!receivers.containsKey(broadcasterType)) {
            list = new ArrayList<>();
            receivers.put(broadcasterType, list);
        } else list = receivers.get(broadcasterType);
        list.add(fermatBroadcastReceiver);
    }

    public void unregisterReceiver(FermatBroadcastReceiver fermatBroadcastReceiver, String appPublicKey) {
        if (!receivers.containsKey(fermatBroadcastReceiver.getBroadcasterType())) return;
        receivers.get(fermatBroadcastReceiver.getBroadcasterType()).remove(fermatBroadcastReceiver);
    }

    public void pushIntent(FermatIntentFilter fermatIntentFilter) {
        if (receivers.containsKey(fermatIntentFilter.getBroadcasterType())) {
            for (FermatBroadcastReceiver fermatBroadcastReceiver : receivers.get(fermatIntentFilter.getBroadcasterType())) {
                fermatBroadcastReceiver.onReceive(fermatIntentFilter.getFermatBundle());
            }
        }
    }
}
