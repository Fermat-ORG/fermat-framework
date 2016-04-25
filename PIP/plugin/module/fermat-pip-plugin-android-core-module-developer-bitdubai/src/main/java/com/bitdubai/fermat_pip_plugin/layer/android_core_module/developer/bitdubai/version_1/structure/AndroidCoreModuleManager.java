package com.bitdubai.fermat_pip_plugin.layer.android_core_module.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.AndroidCoreManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;

/**
 * Created by Natalia   19/01/2016
 *
 */
public class AndroidCoreModuleManager  implements AndroidCoreManager {

    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;
    private BitcoinNetworkManager bitcoinNetworkManager;

    public AndroidCoreModuleManager(WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager,BitcoinNetworkManager bitcoinNetworkManager) {
        this.wsCommunicationsCloudClientManager = wsCommunicationsCloudClientManager;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }



}
