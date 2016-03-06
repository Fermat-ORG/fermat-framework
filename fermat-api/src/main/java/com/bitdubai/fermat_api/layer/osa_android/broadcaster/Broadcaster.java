package com.bitdubai.fermat_api.layer.osa_android.broadcaster;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

/**
 * Created by mati on 2016.02.02..
 */
public interface Broadcaster extends FermatManager {

    void publish(BroadcasterType broadcasterType,String code);

    void publish(BroadcasterType broadcasterType,String appCode,String code);

}
