package com.bitdubai.fermat_api.layer.osa_android.broadcaster;

/**
 * Created by mati on 2016.02.02..
 */
public interface AndroidCoreUtils {


    public void publish(BroadcasterType broadcasterType, String code);

    public void publish(BroadcasterType broadcasterType,String appCode, String code);
}
