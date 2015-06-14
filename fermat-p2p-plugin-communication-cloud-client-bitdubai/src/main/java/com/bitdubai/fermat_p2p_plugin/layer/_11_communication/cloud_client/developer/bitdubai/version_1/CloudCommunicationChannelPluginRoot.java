package com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1;

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

import java.util.*;

/**
 * Created by ciencias on 20.01.15.
 */


/**
 * Hi! I am a cloud service which centralizes the communications between system users.
 */

public class CloudCommunicationChannelPluginRoot implements CommunicationChannel, DealsWithErrors, DealsWithEvents, DealsWithPluginFileSystem, Plugin, Service{



    /**
     * CommunicationChannel Interface member variables.
     */
    private Map<UUID,NetworkServices> networkServices = new HashMap();
    
    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;
    
    /**
     * Plugin Interface member variables.
     */
    private UUID pluginId;

    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;
    private List<EventListener> listenersAdded = new ArrayList<>();


    /**
     * CommunicationChannel Interface implementation.
     */
    @Override
    public OnlineChannel createOnlineChannel() {
        return null;
        //return new CloudOnlineChannel();
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

    }


    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }



    /**
     * DealsWithPluginIdentity methods implementation.
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


    private void incomingConnectionRequestReceived(){
        
        // LOUI TODO: Se debe disparar un evento "IncomingNetworkServiceConnectionRequest" El evento tiene que incluir en el source este modulo, pero ademas el communicationChannel que es del tipo CommunicationChannels, mas networkService del tipo NetworkServices, mas localNetworkServiceId, mas el remoteNetworkServiceId

        
    }

	public Map<UUID, NetworkServices> getNetworkServices() {
		return networkServices;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public PluginFileSystem getPluginFileSystem() {
		return pluginFileSystem;
	}

	public UUID getPluginId() {
		return pluginId;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public List<EventListener> getListenersAdded() {
		return listenersAdded;
	}

}
