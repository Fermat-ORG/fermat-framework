package com.bitdubai.fermat_pip_plugin.layer.android_core_module.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.AndroidCoreManager;

import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;

/**
 * Created by Natalia   19/01/2016
 */
public class AndroidCoreModuleManager implements AndroidCoreManager {

    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;
    private BlockchainManager<ECKey, Transaction> bitcoinNetworkManager;

    public AndroidCoreModuleManager(WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager, BlockchainManager<ECKey, Transaction> bitcoinNetworkManager) {
        this.wsCommunicationsCloudClientManager = wsCommunicationsCloudClientManager;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }


}
