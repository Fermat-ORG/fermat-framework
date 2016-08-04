package com.bitdubai.android_core.app.common.version_1.broadcaster;

import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

/**
 * This version is Deprecated,use BroadcasterInterface instead
 */
public interface BroadcastInterface {

    void notificateBroadcast(String appCode, String code);

    void notificateBroadcast(String appCode, FermatBundle bundle);

    int notificateProgressBroadcast(FermatBundle bundle);


}
