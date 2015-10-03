package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.UUID;

/**
 * Created by rodrigo on 9/23/15.
 */
public class BitcoinCryptoNetworkPluginRoot implements BitcoinNetworkManager, DealsWithEvents, Plugin, Service {
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;
    /**
     * DealsWithEvents interface variable and implementation
     */
    EventManager eventManager;
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Plugin interface variable and implementation
     * @param pluginId
     */
    UUID pluginId;
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     *Service interface variable and implementation
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    @Override
    public void start() throws CantStartPluginException {
         bitcoinCryptoNetworkManager = new BitcoinCryptoNetworkManager(this.eventManager);
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
        return this.serviceStatus;
    }


    @Override
    public void monitorNetworkFromSeed(BlockchainNetworkType blockchainNetworkType, DeterministicSeed deterministicSeed) throws CantMonitorBitcoinNetworkException {
        this.bitcoinCryptoNetworkManager.monitorNetworkFromSeed(blockchainNetworkType, deterministicSeed);
    }

    @Override
    public void monitorNetworkFromWatchingKey(BlockchainNetworkType blockchainNetworkType, DeterministicKey watchingKey) throws CantMonitorBitcoinNetworkException {
        this.bitcoinCryptoNetworkManager.monitorNetworkFromWatchingKey(blockchainNetworkType, watchingKey);
    }
}
