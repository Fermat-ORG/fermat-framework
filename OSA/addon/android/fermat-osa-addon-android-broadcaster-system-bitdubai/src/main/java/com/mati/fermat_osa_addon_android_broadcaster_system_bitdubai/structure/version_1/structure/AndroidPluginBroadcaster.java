package com.mati.fermat_osa_addon_android_broadcaster_system_bitdubai.structure.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.broadcaster.AndroidCoreUtils;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

/**
 * Created by mati on 2016.02.02..
 */
public class AndroidPluginBroadcaster implements Broadcaster {

    AndroidCoreUtils androidCoreBroadcasterUtil;

    public AndroidPluginBroadcaster(AndroidCoreUtils androidCoreBroadcasterUtil) {
        this.androidCoreBroadcasterUtil = androidCoreBroadcasterUtil;
    }

    @Override
    public void publish(BroadcasterType broadcasterType, String code) {
        androidCoreBroadcasterUtil.publish(broadcasterType, code);
    }

    @Override
    @Deprecated
    public void publish(BroadcasterType broadcasterType, String appPublicKeyToOpen, String code) {
        androidCoreBroadcasterUtil.publish(broadcasterType, appPublicKeyToOpen, code);
    }

    @Override
    public void publish(BroadcasterType broadcasterType, String appCode, FermatBundle bundle) {
        androidCoreBroadcasterUtil.publish(broadcasterType, appCode, bundle);
    }

    @Override
    public void publish(BroadcasterType broadcasterType, FermatBundle bundle) {
        androidCoreBroadcasterUtil.publish(broadcasterType, null, bundle);
    }

    @Override
    public int publish(BroadcasterType broadcasterType, FermatBundle bundle, String channelReceiversCode) {
        //todo: tema channel receivers code, armar channels que escuchen eventos
        return androidCoreBroadcasterUtil.publish(broadcasterType, bundle);

    }

}
