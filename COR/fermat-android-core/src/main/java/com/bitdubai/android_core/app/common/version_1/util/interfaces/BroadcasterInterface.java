package com.bitdubai.android_core.app.common.version_1.util.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FermatApps;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

import java.util.UUID;


/**
 * Created by mati on 2016.02.02..
 */
public interface BroadcasterInterface {

    void publish(BroadcasterType broadcasterType, String code);

    void publish(BroadcasterType broadcasterType, String appCode, String code);

    void publish(BroadcasterType broadcasterType, String code, Platforms lauchedPlatform);

    void publish(BroadcasterType broadcasterType, String code, FermatApps fermatApp);

    void publish(BroadcasterType broadcasterType, String appCode, FermatBundle bundle);

    int publish(BroadcasterType broadcasterType, FermatBundle bundle);

    UUID getId();
}
