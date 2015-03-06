package com.bitdubai.fermat_core.layer._10_communication.cloud.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannel;
import com.bitdubai.fermat_api.layer._10_communication.OnlineChannel;
import com.bitdubai.fermat_api.layer._10_communication.ServiceToServiceOnlineConnection;
import com.bitdubai.fermat_api.layer._10_communication.cloud.RejectConnectionRequestReasons;
import com.bitdubai.fermat_core.layer._10_communication.cloud.developer.bitdubai.version_1.structure.CloudOnlineChannel;

import java.util.*;

/**
 * Created by ciencias on 20.01.15.
 */


/**
 * Hi! I am a cloud service which centralizes the communications between system users.
 */

public class CloudCommunicationChannelPluginRoot implements Service, CommunicationChannel, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * CommunicationChannel Interface member variables.
     */
    private Map<UUID,NetworkServices> networkServices = new HashMap();
    
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
     * CommunicationChannel Interface implementation.
     */
    @Override
    public OnlineChannel createOnlineChannel() {

        return new CloudOnlineChannel();
    }

    @Override
    public void registerNetworkService(NetworkServices networkServices, UUID networkService) {
        this.networkServices.put(networkService,networkServices );
    }

    @Override
    public ServiceToServiceOnlineConnection acceptIncomingNetworkServiceConnectionRequest(NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService) {
        return null;
    }

    @Override
    public void rejectIncomingNetworkServiceConnectionRequest(NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService, RejectConnectionRequestReasons reason) {

    }

    @Override
    public void unregisterNetworkService(UUID networkService) {
        this.networkServices.remove(networkService);
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


    private void incomingConnectionRequestReceived(){
        
        // LOUI TODO: Se debe disparar un evento "IncomingNetworkServiceConnectionRequest" El evento tiene que incluir en el source este modulo, pero ademas el communicationChannel que es del tipo CommunicationChannels, mas networkService del tipo NetworkServices, mas localNetworkServiceId, mas el remoteNetworkServiceId

        
    }

}
