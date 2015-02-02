package com.bitdubai.wallet_platform_core.layer._7_crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.PlatformService;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_event.EventManager;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.EventListener;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.EventType;
import com.bitdubai.wallet_platform_api.layer._3_os.DealsWithFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CantCreateCryptoWalletException;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CryptoNetworkManager;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CryptoWallet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * Hi! I am the interface with the bitcoin network. I will provide you with all you need to operate with bitcoins.
 */

public class BitcoinCryptoNetworkService implements PlatformService, CryptoNetworkManager, DealsWithEvents, DealsWithFileSystem, Plugin {

    /**
     * PlatformService Interface member variables.
     */
    ServiceStatus serviceStatus;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * CryptoNetworkManager Interface member variables.
     */
    CryptoWallet cryptoWallet;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;
    
    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() {
        
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

        eventListener = eventManager.getNewListener(EventType.USER_CREATED);
        eventHandler = new WalletCreatedEventHandler();
        ((WalletCreatedEventHandler) eventHandler).setCryptoNetworkManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }


    /**
     * CryptoNetworkManager Interface implementation.
     */

    @Override
    public void loadCryptoWallet(UUID walletId) {

        this.cryptoWallet = new BitcoinCryptoWallet(walletId);

    }

    @Override
    public void createCryptoWallet(UUID walletId) throws CantCreateCryptoWalletException {

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
     * Plugin Interface implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
