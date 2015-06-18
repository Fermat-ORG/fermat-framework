package com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVault;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkMonitoringAgent;
import org.bitcoinj.core.Wallet;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * This plugin interfaces the bitcoin network. It primary mission is to hold the bitcoins for each user on this device.
 * 
 * It handles a bitcoin wallet for each user process transactions upon request from other plugins.
 * 
 * It also monitors the bitcoin network for incoming transactions for any of the device's users.
 * 
 * 
 * * * * * * * *
 */

public class BitcoinCryptoNetworkPluginRoot implements BitcoinCryptoNetworkManager, DealsWithErrors, DealsWithPluginIdentity, DealsWithPluginFileSystem, Service, Plugin{

    /**
     * BitcoinCryptoNetworkManager interface member variables
     */
    CryptoVault cryptoVault;
    BitcoinCryptoNetworkMonitoringAgent bitcoinCryptoNetworkMonitoringAgent;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginIdentity interface member variable
     */
    UUID pluginId;

    /**
     * DealswithPluginFileSystem interface member variable
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * DealsWithError interface implementation
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginIdentity interface implementation
     * @param pluginId
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * DealsWithPluginFileSystem interface implementation
      * @param pluginFileSystem
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }


    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    /**
     * Plugin interface implementation
     * @param uuid
     */
    @Override
    public void setId(UUID uuid) {
    }

    @Override
    public void setVault(CryptoVault cryptoVault) {
        this.cryptoVault = cryptoVault;
    }

    @Override
    public void connectToBitcoinNetwork() {
        bitcoinCryptoNetworkMonitoringAgent = new BitcoinCryptoNetworkMonitoringAgent((Wallet) cryptoVault.getWallet(), cryptoVault.getUserId());
        bitcoinCryptoNetworkMonitoringAgent.setPluginFileSystem(pluginFileSystem);
        bitcoinCryptoNetworkMonitoringAgent.setErrorManager(errorManager);
        bitcoinCryptoNetworkMonitoringAgent.setPluginId(pluginId);

        try {
            bitcoinCryptoNetworkMonitoringAgent.start();
        } catch (CantStartAgentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnectFromBitcoinNetwork() {
        bitcoinCryptoNetworkMonitoringAgent.stop();
    }

    @Override
    public Object getBroadcasters() {
        return bitcoinCryptoNetworkMonitoringAgent.getPeers();
    }
}