package com.bitdubai.wallet_platform_core.layer._7_crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.DealsWithPluginIdentity;
import com.bitdubai.wallet_platform_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.wallet_platform_api.layer._2_event.EventManager;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._3_os.DealsWithFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CryptoWallet;

import java.util.UUID;

/**
 * Created by ciencias on 23.01.15.
 */
public class BitcoinCryptoWallet implements CryptoWallet , DealsWithEvents, DealsWithFileSystem, DealsWithPluginIdentity {

    UUID mWalletId;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;
    

    public BitcoinCryptoWallet (UUID walletId) {

        mWalletId = walletId;

    }


    @Override
    public double getBalance() {
        return 0;
    }

    @Override
    public void sendToAddress(CryptoAddress address, double amount) {

    }


    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     * DealsWithPluginIdentity Interface implementation.
     */

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
    
}
