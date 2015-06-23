package com.bitdubai.fermat_core.layer.p2p_communication;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;

import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.*;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.enums.RejectConnectionRequestReasons;
import com.bitdubai.fermat_core.layer.p2p_communication.cloud.CloudSubsystem;
import com.bitdubai.fermat_core.layer.p2p_communication.cloud_server.CloudServerSubsystem;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

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
    
     private Set<NetworkServices> networkServices = new ConcurrentSkipListSet<NetworkServices>();

  
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
    public void registerNetworkService(NetworkServices networkService) {
        this.networkServices.add(networkService);
        
        ((CommunicationChannel) mCloudPlugin).registerNetworkService(networkService);
        
    }

    @Override
    public void unregisterNetworkService(NetworkServices networkService) {
        this.networkServices.remove(networkService);

        ((CommunicationChannel) mCloudPlugin).unregisterNetworkService(networkService);
        
    }

    @Override
    public void acceptIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService) throws CommunicationChannelNotImplemented {

        switch (communicationChannel) {
            case CLOUD:
                ((CommunicationChannel) mCloudPlugin).acceptIncomingNetworkServiceConnectionRequest(networkService, remoteNetworkService);
                return;
        }
        
        throw new CommunicationChannelNotImplemented();
    }

    @Override
    public void rejectIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService, RejectConnectionRequestReasons reason) throws CommunicationChannelNotImplemented {

        switch (communicationChannel) {

            case CLOUD:
                ((CommunicationChannel) mCloudPlugin).rejectIncomingNetworkServiceConnectionRequest(networkService, remoteNetworkService, reason);

        }

        throw new CommunicationChannelNotImplemented();
    }

    @Override
    public ServiceToServiceOnlineConnection getActiveNetworkServiceConnection(CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService) {
        return ((CommunicationChannel) mCloudPlugin).getActiveNetworkServiceConnection(networkService, remoteNetworkService);
    }

    @Override
    public Collection<String> getActiveNetworkServiceConnectionIdentifiers(NetworkServices networkService) {
        return ((CommunicationChannel) mCloudPlugin).getActiveNetworkServiceConnectionIdentifiers(networkService);
    }

    /**
     * This is the primary method to connect a local network service to a remote network service.
     */
    @Override
    public void requestConnectionTo(NetworkServices networkService, String remoteNetworkService) throws CantConnectToRemoteServiceException{
        ((CommunicationChannel) mCloudPlugin).requestConnectiontTo(networkService, remoteNetworkService);
    }
    
    


}
