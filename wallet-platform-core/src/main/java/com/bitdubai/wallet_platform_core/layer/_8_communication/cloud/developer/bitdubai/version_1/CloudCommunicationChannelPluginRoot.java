package com.bitdubai.wallet_platform_core.layer._8_communication.cloud.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.DealsWithFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._8_communication.CommunicationChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */


/**
 * Hi! I am a cloud service which centralizes the communications between system users.
 */

public class CloudCommunicationChannelPluginRoot implements Service, CommunicationChannel, DealsWithEvents, DealsWithErrors, DealsWithFileSystem, Plugin {


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus;
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

    public CloudCommunicationChannelPluginRoot() {

        this.serviceStatus = ServiceStatus.CREATED;
    }

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
