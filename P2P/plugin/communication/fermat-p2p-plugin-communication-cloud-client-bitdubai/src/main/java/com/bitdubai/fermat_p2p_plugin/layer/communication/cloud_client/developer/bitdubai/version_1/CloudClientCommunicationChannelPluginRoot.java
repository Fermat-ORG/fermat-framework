package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CantConnectToRemoteServiceException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication. CommunicationChannel;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.OnlineChannel;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ServiceToServiceOnlineConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.enums.RejectConnectionRequestReasons;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientCommunicationManager;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ciencias on 20.01.15.
 */

// TODO; JORE: Cuando llega una llamada entrante disparar un evento para que el Network Service venga a tomarla.

/**
 * Hi! I am a cloud service which centralizes the communications between system users.
 */

public class CloudClientCommunicationChannelPluginRoot implements CommunicationChannel, DealsWithErrors, DealsWithEvents, DealsWithPluginFileSystem, Plugin, Service{

    /**
     * CommunicationChannel Interface member variables.
     */
	private CommunicationChannelAddress serverAddress;
	private String serverPublicKey;
	private CloudClientCommunicationManager cloudClient;
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
    
    public CloudClientCommunicationChannelPluginRoot(final String serverHost, final Integer serverPort, final String serverPublicKey){
    	this();
    	this.serverAddress = CommunicationChannelAddressFactory.constructCloudAddress(serverHost, serverPort);
    	this.serverPublicKey = serverPublicKey;
    }
    
    public CloudClientCommunicationChannelPluginRoot(final UUID pluginId, final EventManager eventManager, 
    		final ErrorManager errorManager, final PluginFileSystem pluginFileSystem){
    	this.pluginId = UUID.fromString(pluginId.toString());
    	this.eventManager = eventManager;
    	this.errorManager = errorManager;
    	this.pluginFileSystem = pluginFileSystem;
    }

	@Override
	public OnlineChannel createOnlineChannel() {
		return null;
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
		} catch (CloudCommunicationException e) {
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
		} catch (CloudCommunicationException e) {
			return null;
		}
	}

	@Override
	public void requestConnectiontTo(NetworkServices networkServices, String remoteNetworkService) throws CantConnectToRemoteServiceException {
		try {
			cloudClient.getNetworkServiceClient(networkServices).requestVPNConnection(remoteNetworkService);
		} catch (CloudCommunicationException e) {
			throw new CantConnectToRemoteServiceException(e.getMessage());
		}
	}

	@Override
	public Collection<String> getIncomingNetworkServiceConnectionRequests(final NetworkServices networkService) {
		try {
			return cloudClient.getNetworkServiceClient(networkService).getPendingVPNRequests();
		} catch (CloudCommunicationException e) {
			return null;
		}
	}

	@Override
	public void acceptIncomingNetworkServiceConnectionRequest(final NetworkServices networkService, String remoteNetworkService) {
		try {
			cloudClient.getNetworkServiceClient(networkService).acceptPendingVPNRequest(remoteNetworkService);
		} catch (CloudCommunicationException | FMPException e) {
			return;
		}
	}
		


	@Override
	public ServiceToServiceOnlineConnection getActiveNetworkServiceConnection(final NetworkServices networkService, final String remoteNetworkService) {
		try {
			return cloudClient.getNetworkServiceClient(networkService).getActiveVPN(remoteNetworkService);
		} catch (CloudCommunicationException e) {
			System.out.println(errorManager.hashCode());
			return null;
		}
	}

	@Override
	public Collection<String> getActiveNetworkServiceConnectionIdentifiers(NetworkServices networkService) {
		try {
			return cloudClient.getNetworkServiceClient(networkService).getActiveVPNIdentifiers();
		} catch (CloudCommunicationException e) {
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
    
    public void setCloudServerInfo(final String serverHost, final Integer serverPort, final String serverPublicKey){
    	this.serverAddress = CommunicationChannelAddressFactory.constructCloudAddress(serverHost, serverPort);
    	this.serverPublicKey = serverPublicKey;
    }

	// TODO JORGE: Integrarse con LOCAL DEVICE ADDON para que el sea el que maneje la identidad del dispositivo.
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
    	ExecutorService executor = Executors.newCachedThreadPool();
    	String clientKey = AsymmectricCryptography.createPrivateKey();
		cloudClient = new CloudClientCommunicationManager(serverAddress, executor, clientKey, serverPublicKey);
		try{
    		cloudClient.start();
    		cloudClient.requestConnectionToServer();
    		this.serviceStatus = ServiceStatus.STARTED;
    	} catch(CommunicationException ex){
			StringBuilder contextBuilder = new StringBuilder();
			contextBuilder.append("Client Public Key: " + cloudClient.getPublicKey() + " ");
			contextBuilder.append("Server Address: " + cloudClient.getAddress().toString()+ " ");

			CantStartPluginException pluginException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, ex, contextBuilder.toString(), "The Cloud Client Failed To Initialize");

			this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CLOUD_CHANNEL, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginException);

			stop();
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
		} catch (CloudCommunicationException e) {
				return;
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
