package com.bitdubai.wallet_platform_plugin.layer._13_module.wallet_store.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._13_module.wallet_store.CantRecordInstalledWalletException;
import com.bitdubai.wallet_platform_api.layer._13_module.wallet_store.CantRecordUninstalledWalletException;
import com.bitdubai.wallet_platform_api.layer._13_module.wallet_store.WalletStore;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.*;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.DealsWithFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.PluginFileSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 30.12.14.
 */
public class WalletStorePluginRoot implements Service, WalletStore, DealsWithEvents, DealsWithErrors, DealsWithFileSystem, Plugin {

    /**
     * PlatformService Interface member variables.
     */
    ServiceStatus serviceStatus;
    List<EventListener> listenersAdded = new ArrayList<>();

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


    public WalletStorePluginRoot(){
        this.serviceStatus = ServiceStatus.CREATED;
    }

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

        eventListener = eventManager.getNewListener(EventType.WALLET_INSTALLED);
        eventHandler = new BegunWalletInstallationEventHandler();
        ((BegunWalletInstallationEventHandler) eventHandler).setWalletStore(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.WALLET_UNINSTALLED);
        eventHandler = new BegunWalletInstallationEventHandler();
        ((BegunWalletInstallationEventHandler) eventHandler).setWalletStore(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {

    }
    
    @Override
    public void resume() {
        
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
        return this.serviceStatus;
    }


    /**
     * WalletStore Interface implementation.
     */

    @Override
    public void recordInstalledWallet(UUID walletId) throws CantRecordInstalledWalletException {

    }

    @Override
    public void recordUninstalledwallet(UUID walletId) throws CantRecordUninstalledWalletException {

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

    }

    /**
     *DealWithErrors Interface implementation. 
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

}
