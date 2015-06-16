package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.cry_3_crypto_module.Crypto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 20/02/15.
 */

/**
 * This Plug-in has the responsibility to manage the relationship between users and crypto addresses.
 *
 * * * * * *
 */

public class WalletAddressBookCryptoPluginRoot implements Crypto, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, DealsWithErrors, DealsWithEvents, Plugin, Service {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;


    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();







    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     *DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     * Plugin methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

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


        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }




}
