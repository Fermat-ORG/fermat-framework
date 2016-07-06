package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

/**
 * Created by Matias Furszyfer on 2016.07.06..
 * //todo: i have to improve this class
 */
public abstract class FermatBroadcastReceiver {

    private BroadcasterType broadcasterType;

    public abstract void onReceive(FermatBundle fermatBundle);

    public void setBroadcasterType(BroadcasterType broadcasterType) {
        this.broadcasterType = broadcasterType;
    }

    public BroadcasterType getBroadcasterType() {
        return broadcasterType;
    }
}
