package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

/**
 * Created by Matias Furszyfer on 2016.07.06..
 */
public class FermatIntentFilter {

    private FermatBundle fermatBundle;
    private BroadcasterType broadcasterType;

    public FermatIntentFilter() {
    }

    public FermatIntentFilter(BroadcasterType broadcasterType) {
    }

    public FermatBundle getFermatBundle() {
        return fermatBundle;
    }

    public void setFermatBundle(FermatBundle fermatBundle) {
        this.fermatBundle = fermatBundle;
    }

    public BroadcasterType getBroadcasterType() {
        return broadcasterType;
    }

    public void setBroadcasterType(BroadcasterType broadcasterType) {
        this.broadcasterType = broadcasterType;
    }
}
