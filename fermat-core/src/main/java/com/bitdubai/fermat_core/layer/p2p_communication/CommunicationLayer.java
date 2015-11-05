package com.bitdubai.fermat_core.layer.p2p_communication;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;

import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_core.layer.p2p_communication.ws_cloud_server.WsCommunicationCloudServerSubsystem;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.*;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.enums.RejectConnectionRequestReasons;
import com.bitdubai.fermat_core.layer.p2p_communication.cloud_client.CloudClientSubsystem;
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


    private Plugin mWsCommunicationCloudServerPlugin;

    /**
     * CommunicationLayerManager Interface member variables.
     */

    /**
     * I keep track of the network services registered because each communication channel can go online and offline at 
     * anytime, and when it is online, I have to register the network services by myself.
     * * * 
     */
    //TODO: JORGE : Agregar a este listado los network services al momento del registro.
    
     private Set<NetworkServices> networkServices = new ConcurrentSkipListSet<NetworkServices>();

  
    public Plugin getCloudPlugin() {
        return mCloudPlugin;
    }

    public Plugin getCloudServerPlugin(){
        return mCloudServerPlugin;
    }

    public Plugin getWsCommunicationCloudServerPlugin(){
        return mWsCommunicationCloudServerPlugin;
    }


    @Override
    public void start() throws CantStartLayerException {


        /**
         * For now, the only way to communicate with other devices is through a cloud service.
         */
        CommunicationSubsystem cloudClientSubsystem = new CloudClientSubsystem();

        try {

            cloudClientSubsystem.start();
            mCloudPlugin = cloudClientSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }

        /**
         *
         */
        CommunicationSubsystem cloudServerSubsystem = new CloudServerSubsystem();

        try {

            cloudServerSubsystem.start();
            mCloudServerPlugin = cloudServerSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }


        /**
         * Initialize the WsCommunicationCloudServerSubsystem
         */
        WsCommunicationCloudServerSubsystem wsCommunicationCloudServerSubsystem = new WsCommunicationCloudServerSubsystem();

        try {

            wsCommunicationCloudServerSubsystem.start();
            mWsCommunicationCloudServerPlugin = wsCommunicationCloudServerSubsystem.getPlugin();

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
    public void registerNetworkService(NetworkServices networkService, String networkServicePublicKey) throws CommunicationException{
        this.networkServices.add(networkService);
        
        ((CommunicationChannel) mCloudPlugin).registerNetworkService(networkService, networkServicePublicKey);
        
    }

    @Override
    public void unregisterNetworkService(NetworkServices networkService) throws CommunicationException{
        this.networkServices.remove(networkService);

        ((CommunicationChannel) mCloudPlugin).unregisterNetworkService(networkService);

    }

    @Override
    public void acceptIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService) throws CommunicationException {

        switch (communicationChannel) {
            case CLOUD:
                ((CommunicationChannel) mCloudPlugin).acceptIncomingNetworkServiceConnectionRequest(networkService, remoteNetworkService);
                return;
        }
        
        throw new CommunicationChannelNotImplementedException(CommunicationChannelNotImplementedException.DEFAULT_MESSAGE, null, "Communication Channel: " + communicationChannel.toString(), "This Channel might not be working on the switch statement");
    }

    @Override
    public void rejectIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService, RejectConnectionRequestReasons reason) throws CommunicationException {

        switch (communicationChannel) {

            case CLOUD:
                ((CommunicationChannel) mCloudPlugin).rejectIncomingNetworkServiceConnectionRequest(networkService, remoteNetworkService, reason);

        }

        throw new CommunicationChannelNotImplementedException(CommunicationChannelNotImplementedException.DEFAULT_MESSAGE, null, "Communication Channel: " + communicationChannel.toString(), "This Channel might not be working on the switch statement");
    }

    @Override
    public ServiceToServiceOnlineConnection getActiveNetworkServiceConnection(CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService) throws CommunicationException{
        return ((CommunicationChannel) mCloudPlugin).getActiveNetworkServiceConnection(networkService, remoteNetworkService);
    //TODO: JORGE usar el objeto LayerServiceToServiceOnlineConnection para encapsular la conexion real.
    }

    /**
     * (non-Javadoc)
     *
     * @see CommunicationLayerManager#getNetworkServiceChannelPublicKey(NetworkServices)
     *
     */
    @Override
    public String getNetworkServiceChannelPublicKey(NetworkServices networkService) throws NetworkServiceNotRegisteredException {
        return ((CommunicationChannel) mCloudPlugin).getNetworkServiceChannelPublicKey(networkService);
    }

    @Override
    public Collection<String> getActiveNetworkServiceConnectionIdentifiers(NetworkServices networkService) throws CommunicationException{
        return ((CommunicationChannel) mCloudPlugin).getActiveNetworkServiceConnectionIdentifiers(networkService);
    }

    /**
     * This is the primary method to connect a local network service to a remote network service.
     */
    @Override
    public void requestConnectionTo(NetworkServices networkService, String remoteNetworkService) throws CommunicationException{
        ((CommunicationChannel) mCloudPlugin).requestConnectiontTo(networkService, remoteNetworkService);
    }

}
