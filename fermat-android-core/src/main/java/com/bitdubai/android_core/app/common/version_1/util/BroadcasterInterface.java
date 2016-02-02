package com.bitdubai.android_core.app.common.version_1.util;

import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;

/**
 * Created by mati on 2016.02.02..
 */
public interface BroadcasterInterface {

    void publish(BroadcasterType broadcasterType, String code);

}
