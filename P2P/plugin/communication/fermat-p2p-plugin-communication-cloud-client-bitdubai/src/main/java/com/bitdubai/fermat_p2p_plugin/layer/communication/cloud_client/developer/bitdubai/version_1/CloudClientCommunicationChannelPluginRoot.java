package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication. CommunicationChannel;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ServiceToServiceOnlineConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.enums.RejectConnectionRequestReasons;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.exceptions.CloudConnectionException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientManager;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ciencias on 20.01.15.
 */


/**
 * Hi! I am a cloud service which centralizes the communications between system users.
 */

public class CloudClientCommunicationChannelPluginRoot implements CommunicationChannel, DealsWithErrors, DealsWithEvents, DealsWithPluginFileSystem, Plugin, Service{

    /**
     * CommunicationChannel Interface member variables.
     */
	private CloudClientManager cloudClient;
    private Set<NetworkServices> networkServices = new HashSet<NetworkServices>();
    
    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private ErrorManager errorManager;

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
    private List<EventListener> listenersAdded = new ArrayList<EventListener>();
    
    
    public CloudClientCommunicationChannelPluginRoot() {
		// TODO Auto-generated constructor stub
    	this.pluginId = UUID.randomUUID();
    	this.eventManager = null;
    	this.errorManager = null;
	}
    
    public CloudClientCommunicationChannelPluginRoot(final UUID pluginId, final EventManager eventManager, 
    		final ErrorManager errorManager, final PluginFileSystem pluginFileSystem){
    	this.pluginId = UUID.fromString(pluginId.toString());
    	this.eventManager = eventManager;
    	this.errorManager = errorManager;
    	this.pluginFileSystem = pluginFileSystem;
    }
    
    /**
     * CommunicationChannel Interface implementation.
     */	
    @Override
	public String getChannelPublicKey() {
		return cloudClient.getPublicKey();
	}

	@Override
	public void registerNetworkService(final NetworkServices networkService) {
		try {
			cloudClient.registerNetworkService(networkService);
		} catch (CloudConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(errorManager.hashCode());
		}
	}

	@Override
	public void unregisterNetworkService(final NetworkServices networkService) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getNetworkServiceChannelPublicKey(final NetworkServices networkService) {
		try {
			return cloudClient.getNetworkServiceClient(networkService).getPublicKey();
		} catch (CloudConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Collection<String> getIncomingNetworkServiceConnectionRequests(final NetworkServices networkService) {
		try {
			return cloudClient.getNetworkServiceClient(networkService).getPendingVPNRequests();
		} catch (CloudConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(errorManager.hashCode());
			return null;
		}
	}

	@Override
	public void acceptIncomingNetworkServiceConnectionRequest(final NetworkServices networkService, String remoteNetworkService) {
		try {
			cloudClient.getNetworkServiceClient(networkService).acceptPendingVPNRequest(remoteNetworkService);
		} catch (CloudConnectionException | FMPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(errorManager.hashCode());
		}
	}
		


	@Override
	public ServiceToServiceOnlineConnection getActiveNetworkServiceConnection(final NetworkServices networkService, final String remoteNetworkService) {
		try {
			return cloudClient.getNetworkServiceClient(networkService).getActiveVPN(remoteNetworkService);
		} catch (CloudConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(errorManager.hashCode());
			return null;
		}
	}

	@Override
	public void rejectIncomingNetworkServiceConnectionRequest(final NetworkServices networkService, String remoteNetworkService,
			RejectConnectionRequestReasons reason) {
		// TODO Auto-generated method stub
	}
    
    /**
     * DealsWithPluginFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(final PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }
    
    /**
     *DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(final ErrorManager errorManager) {
    	this.errorManager = errorManager;
    }


    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(final EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(final UUID pluginId) {
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
        //EventListener eventListener;
        //EventHandler eventHandler;
    	String serverHost = "localhost";
    	Integer serverPort = Integer.valueOf(9090);
    	CommunicationChannelAddress serverAddress = CommunicationChannelAddressFactory.constructCloudAddress(serverHost, serverPort);
    	String serverKey = "04F0F591F89E3CA948824F3CA8FD7D2115AE20B801EDE4CA090E3DA1856C1AC199CAB9BCF755162159C3C999F921ACE78B9529DFE67715C321DA8208B483DC74DB";
    	ExecutorService executor = Executors.newCachedThreadPool();
    	String clientKey = AsymmectricCryptography.createPrivateKey();
    	try{ 
    		cloudClient = new CloudClientManager(serverAddress, executor, clientKey, serverKey);
    		cloudClient.start();
    		cloudClient.requestConnectionToServer();
    		this.serviceStatus = ServiceStatus.STARTED;
    	} catch(Exception ex){
    		ex.printStackTrace();
    	}
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
        try {
			this.cloudClient.stop();
		} catch (CloudConnectionException e) {
			e.printStackTrace();
		}
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

	public Collection<NetworkServices> getNetworkServices() {
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
