package com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
//import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannel;
import com.bitdubai.fermat_api.layer._10_communication.OnlineChannel;
import com.bitdubai.fermat_api.layer._10_communication.ServiceToServiceOnlineConnection;
import com.bitdubai.fermat_api.layer._10_communication.cloud.RejectConnectionRequestReasons;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 26/04/15.
 */
public class CloudServerCommunicationPluginRoot implements Service, CommunicationChannel, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem,Plugin {

    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;
    private List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * UsesFileSystem Interface member variables.
     */
    //private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    //private UUID pluginId;
    
    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

    	//EventListener eventListener;
    	//EventHandler eventHandler;

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
     * Communication channels interface implementation.
     */

    @Override
    public OnlineChannel createOnlineChannel() {
        return null;
    }

    @Override
    public void unregisterNetworkService(UUID networkService) {

    }

    @Override
    public void registerNetworkService(NetworkServices networkServices, UUID networkService) {

    }

    @Override
    public ServiceToServiceOnlineConnection acceptIncomingNetworkServiceConnectionRequest(NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService) {
        return null;
    }

    @Override
    public void rejectIncomingNetworkServiceConnectionRequest(NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService, RejectConnectionRequestReasons reason) {

    }

    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
    	//this.pluginFileSystem = pluginFileSystem;
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
        //this.pluginId = pluginId;
    }

}
