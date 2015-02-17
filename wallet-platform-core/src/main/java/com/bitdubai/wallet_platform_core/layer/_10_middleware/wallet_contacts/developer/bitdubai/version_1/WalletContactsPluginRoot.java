package com.bitdubai.wallet_platform_core.layer._10_middleware.wallet_contacts.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._10_middleware.MiddlewareEngine;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.PluginFileSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 17/02/15.
 */

/**
 * This plugin manages list of contacts. When the contacts are system users the actual information and pictures are 
 * handled by the user network service. When the contacts are non system users, the information is kipped by this plugin.
 * 
 * A contact list is associated with one or more wallets. This is useful if a user want to share contacts between 
 * wallets. A single wallet can be part of more tha one list also.
 * 
 * * * * * * 
 */

public class WalletContactsPluginRoot implements Service, MiddlewareEngine, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
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

    /**
     * WalletManager methods implmentation.
     */


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
