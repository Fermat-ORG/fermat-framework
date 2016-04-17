package com.bitdubai.fermat_pip_plugin.layer.android_core_module.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainConnectionStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.NetworkStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetBitcoinNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetCommunicationNetworkStatusException;
import com.bitdubai.fermat_api.AndroidCoreManager;

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


    @Override
    public NetworkStatus getFermatNetworkStatus() throws CantGetCommunicationNetworkStatusException{
        try {
        if( this.wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().isConnected())
            return NetworkStatus.CONNECTED;
        else
            return NetworkStatus.DISCONNECTED;
        } catch (Exception e) {
            throw new CantGetCommunicationNetworkStatusException(CantGetCommunicationNetworkStatusException.DEFAULT_MESSAGE,e,"","Cant Get Cloud Cient Network Connection Status");
        }
    }

    @Override
    public NetworkStatus getBitcoinNetworkStatus(BlockchainNetworkType blockchainNetworkType) throws CantGetBitcoinNetworkStatusException{
        try {
            if(bitcoinNetworkManager.getBlockchainConnectionStatus(blockchainNetworkType).isConnected())
             return NetworkStatus.CONNECTED;
            else
                return NetworkStatus.DISCONNECTED;
        } catch (CantGetBlockchainConnectionStatusException e) {
            throw new CantGetBitcoinNetworkStatusException(CantGetBitcoinNetworkStatusException.DEFAULT_MESSAGE,e,"","Cant Get Bitcoin Network Connection Status");
        }
    }

    @Override
    public NetworkStatus getPrivateNetworkStatus() {
        return NetworkStatus.CONNECTED;
    }

    public void setWsCommunicationsCloudClientManager(WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager) {
        this.wsCommunicationsCloudClientManager = wsCommunicationsCloudClientManager;
    }
}
