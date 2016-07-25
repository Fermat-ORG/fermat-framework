package com.bitdubai.fermat_api.layer.osa_android;

import com.bitdubai.fermat_api.layer.all_definition.callback.FermatReceiver;

/**
 * Created by mati on 2016.07.14..
 */
public interface NetworkStateReceiver extends FermatReceiver<NetworkStateReceiver> {

    void networkAvailable(DeviceNetwork deviceNetwork);

    void networkUnavailable();

}
