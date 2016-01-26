package com.bitdubai.fermat_pip_plugin.layer.android_core_module.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_pip_api.all_definition.enums.NetworkStatus;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreManager;

/**
 * Created by Natalia   19/01/2016
 *
 */
public class AndroidCoreModuleManager  implements AndroidCoreManager {

    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    public AndroidCoreModuleManager(WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager) {
        this.wsCommunicationsCloudClientManager = wsCommunicationsCloudClientManager;
    }


    @Override
    public NetworkStatus getFermatNetworkStatus() {
        return NetworkStatus.CONNECTED;
    }

    @Override
    public NetworkStatus getBitcoinNetworkStatus() {
        return NetworkStatus.CONNECTED;
    }

    @Override
    public NetworkStatus getPrivateNetworkStatus() {
        return NetworkStatus.CONNECTED;
    }

    public void setWsCommunicationsCloudClientManager(WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager) {
        this.wsCommunicationsCloudClientManager = wsCommunicationsCloudClientManager;
    }
}
