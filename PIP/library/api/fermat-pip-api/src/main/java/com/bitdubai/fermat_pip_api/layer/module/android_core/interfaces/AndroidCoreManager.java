package com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces;

import com.bitdubai.fermat_pip_api.all_definition.enums.NetworkStatus;

/**
 * Created by natalia on 19/01/16.
 */
public interface AndroidCoreManager {


    NetworkStatus getFermatNetworkStatus();

    NetworkStatus getBitcoinNetworkStatus();

    NetworkStatus getPrivateNetworkStatus();

}
