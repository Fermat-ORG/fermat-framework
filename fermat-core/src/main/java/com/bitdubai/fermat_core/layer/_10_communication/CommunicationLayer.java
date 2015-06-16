package com.bitdubai.fermat_core.layer._10_communication;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;

import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.p2p_communication.*;
import com.bitdubai.fermat_api.layer.p2p_communication.cloud.RejectConnectionRequestReasons;
import com.bitdubai.fermat_core.layer._10_communication.cloud.CloudSubsystem;
import com.bitdubai.fermat_core.layer._10_communication.cloud_server.CloudServerSubsystem;

import java.util.*;

/**
 * Created by ciencias on 31.12.14.
 */

/**
 * I am going to establish several communication channels. I will use each one when appropriate.
 */

public class CommunicationLayer implements PlatformLayer, CommunicationLayerManager {

    private Plugin mCloudPlugin;
    private Plugin mCloudServerPlugin;

    /**
     * CommunicationLayerManager Interface member variables.
     */

    /**
     * I keep track of the network services registered because each communication channel can go online and offline at 
     * anytime, and when it is online, I have to register the network services by myself.
     * * * 
     */
    
     private Map<UUID,NetworkServices> networkServices = new HashMap();

  
    public Plugin getCloudPlugin() {
        return mCloudPlugin;
    }

    public Plugin getCloudServerPlugin(){
        return mCloudServerPlugin;
    }

    /**
     * PlatformLayer Interface implementation.
     */
    
    @Override
    public void start() throws CantStartLayerException {


        /**
         * For now, the only way to communicate with other devices is through a cloud service.
         */
        CommunicationSubsystem cloudSubsystem = new CloudSubsystem();

        try {
            cloudSubsystem.start();
            mCloudPlugin = ((CloudSubsystem) cloudSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }

        /**
         * For now, the only way to communicate with other devices is through a cloud service.
         */
        CommunicationSubsystem cloudServerSubsystem = new CloudServerSubsystem();

        try {
            cloudServerSubsystem.start();
            mCloudServerPlugin = ((CloudServerSubsystem) cloudServerSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }
    }


    /**
     * CommunicationLayerManager Interface implementation.
     */


    @Override
    public void registerNetworkService(NetworkServices networkServices,UUID networkService) {
        this.networkServices.put(networkService,networkServices );
        
        ((CommunicationChannel) mCloudPlugin).registerNetworkService(networkServices,networkService);
        
    }

    @Override
    public void unregisterNetworkService(UUID networkService) {
        this.networkServices.remove(networkService);

        ((CommunicationChannel) mCloudPlugin).unregisterNetworkService(networkService);
        
    }

    @Override
    public ServiceToServiceOnlineConnection acceptIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService) throws CommunicationChannelNotImplemented {

        switch (communicationChannel) {

            case CLOUD:
                return ((CommunicationChannel) mCloudPlugin).acceptIncomingNetworkServiceConnectionRequest(networkService, localNetworkService, remoteNetworkService);

        }
        
        throw new CommunicationChannelNotImplemented();
    }

    @Override
    public void rejectIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService, RejectConnectionRequestReasons reason) throws CommunicationChannelNotImplemented {

        switch (communicationChannel) {

            case CLOUD:
                ((CommunicationChannel) mCloudPlugin).rejectIncomingNetworkServiceConnectionRequest(networkService, localNetworkService, remoteNetworkService, reason);

        }

        throw new CommunicationChannelNotImplemented();
    }

    /**
     * This is the primary method to connect a local network service to a remote network service.
     */
    public ServiceToServiceOnlineConnection connectTo (NetworkServices networkServices, UUID remoteNetworkService) throws CantConnectToRemoteServiceException {

        LayerServiceToServiceOnlineConnection layerServiceToServiceOnlineConnection = new LayerServiceToServiceOnlineConnection(networkServices, remoteNetworkService);


        try
        {
            layerServiceToServiceOnlineConnection.connect();
        }
        catch (CantConnectToRemoteServiceException cantConnectToRemoteServiceException)
        {
            System.err.println("CantConnectToUserException: " + cantConnectToRemoteServiceException.getMessage());

            /**
             * I can't do anything with this exception here. I throw it again.
             */
            throw cantConnectToRemoteServiceException;
        }

        return (ServiceToServiceOnlineConnection) layerServiceToServiceOnlineConnection;

    }
    
    


}
