package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Wallet;

import java.util.List;

/**
 * Created by rodrigo on 10/4/15.
 */
public class BitcoinCryptoNetworkManager {
    Wallet wallet=null;

    /**
     * Platform variables
     */
    EventManager eventManager;
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor
     * @param eventManager
     * @param pluginDatabaseSystem
     */
    public BitcoinCryptoNetworkManager(EventManager eventManager, PluginDatabaseSystem pluginDatabaseSystem) {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Monitor the bitcoin network with the passes Key Lists.
     * @param blockchainNetworkTypes
     * @param keyList
     */
    public void monitorNetworkFromKeyList(List<BlockchainNetworkType> blockchainNetworkTypes, List<ECKey> keyList){
        /**
         * This method will be called from agents from the Vaults. New keys may be added on each call or not.
         * If no new keys have been added and I'm already monitoring the network, then I won't do anything
         */
        if (wallet != null){ //this means I have already been called
            /**
             * I will compare the amount of keys that I have in the wallet againts the ones that are been passed in this method
             */
            if (wallet.getImportedKeys().size() == keyList.size())
                return;
            else{
                //this means new keys been added, I probably have to reset the network. I need to investigate this more.
            }
        } else {
            /**
             * I have never been called before, I will start the agent for all networks passed with the list of keys
             */
            for (BlockchainNetworkType blockchainNetworkType : blockchainNetworkTypes){
                wallet = Wallet.fromKeys(BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType), keyList);
                BitcoinCryptoNetworkMonitor bitcoinCryptoNetworkMonitor = new BitcoinCryptoNetworkMonitor(this.eventManager, this.pluginDatabaseSystem, wallet);
                try {
                    bitcoinCryptoNetworkMonitor.start();
                } catch (CantStartAgentException e) {
                    //todo handle
                }
            }
        }
    }
}
