package com.bitdubai.fermat_api.layer.osa_android.broadcaster;

/**
 * Created by mati on 2016.02.02..
 */
public interface AndroidCoreUtils {


    void publish(BroadcasterType broadcasterType, String code);

    void publish(BroadcasterType broadcasterType, String appCode, String code);

    void publish(BroadcasterType broadcasterType, String appCode, FermatBundle bundle);

    int publish(BroadcasterType broadcasterType, FermatBundle bundle);
}
