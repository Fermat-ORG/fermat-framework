package com.bitdubai.wallet_platform_core.layer._10_network_service.wallet_community.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._10_network_service.wallet_community.WalletCommunityManager;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventType;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._10_network_service.NetworkService;
import com.bitdubai.wallet_platform_core.layer._10_network_service.wallet_community.developer.bitdubai.version_1.EventHandlers.FinishedWalletinstallationEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 17/02/15.
 */

/**
 * This Plugin maintains a list of Users for each wallet installed. I will include a pre-configured number of users per
 * wallet type, prioritizing the ones which location is closer to the users average location.
 * 
 * 
 * It also maintains a general list of system users also by average location as the main community.
 *
 * * * * 
 */
// LOUI; TODO; Escuchar el evento FINISHED_WALLET_INSTALATION que -- lo debe disparar el wallet manager cuando todo lo que ya sabemos termino--. (crea un metodo privado finishedWalletInstallation en el Wallet Manger y disparalo desde ahi)

public class WalletCommunityPluginRoot implements Service, NetworkService, WalletCommunityManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem,Plugin {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;



    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
        /**
         * I will initialize the handling of platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;
/*
        eventListener = eventManager.getNewListener(EventType.FINISHED_WALLET_INSTALLATION);
        eventHandler = new FinishedWalletinstallationEventHandler();
        ((FinishedWalletinstallationEventHandler) eventHandler).setWalletCommunityManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);
  */
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
