package com.bitdubai.fermat_core.layer._9_communication;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;

import com.bitdubai.fermat_api.layer._10_network_service.NetworkService;
import com.bitdubai.fermat_api.layer._10_network_service.intra_user.IntraUser;

import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer._9_communication.*;
import com.bitdubai.fermat_api.layer._9_communication.cloud.RejectConnectionRequestReasons;
import com.bitdubai.fermat_core.layer._9_communication.cloud.CloudSubsystem;

import java.util.*;

/**
 * Created by ciencias on 31.12.14.
 */

/**
 * I am going to establish several communication channels. I will use each one when appropriate.
 */

public class CommunicationLayer implements PlatformLayer, CommunicationLayerManager {

    //private Plugin mBluetoohPlugin;

    private Plugin mCloudPlugin;
    /*
    private Plugin mEmailPlugin;
    private Plugin mLanPlugin;
    private Plugin mNfcPlugin;
    private Plugin mP2PPlugin;
    private Plugin mSMSPlugin;
    private Plugin mUriPlugin;
    */
    

     /*
    public Plugin getBluetoohPlugin() {
        return mBluetoohPlugin;
    }

*/
     private Map<UUID,NetworkServices> networkServices = new HashMap();

  
    public Plugin getCloudPlugin() {
        return mCloudPlugin;
    }
    /*

    public Plugin getEmailPlugin() {
        return mEmailPlugin;
    }

    public Plugin getLanPlugin() {
        return mLanPlugin;
    }

    public Plugin getNfcPlugin() {
        return mNfcPlugin;
    }

    public Plugin getP2PPlugin() {
        return mP2PPlugin;
    }

    public Plugin getSMSPlugin() {
        return mSMSPlugin;
    }

    public Plugin getUriPlugin() {
        return mUriPlugin;
    }
    */

    /**
     * CommunicationLayer Interface implementation.
     */

    
    /**
     * Note that it is not possible for the caller of this method to specify which is local user. This user is grabbed
     * from the current logged in user stored on the Platform context. 
     */




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
    public ServiceToServiceOnlineConnection acceptIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService) throws  CommunicationChannelNotImplemented {

        switch (communicationChannel) {

            case CLOUD:
                return ((CommunicationChannel) mCloudPlugin).acceptIncomingNetworkServiceConnectionRequest(networkService, localNetworkService, remoteNetworkService);

        }
        
        throw new CommunicationChannelNotImplemented();
    }

    @Override
    public ServiceToServiceOnlineConnection rejectIncomingNetworkServiceConnectionRequest(CommunicationChannels communicationChannel, NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService, RejectConnectionRequestReasons reason) throws CommunicationChannelNotImplemented {

        switch (communicationChannel) {

            case CLOUD:
                return ((CommunicationChannel) mCloudPlugin).rejectIncomingNetworkServiceConnectionRequest(networkService, localNetworkService, remoteNetworkService, reason);

        }

        throw new CommunicationChannelNotImplemented();
    }

    /**
     * This is the primary method to connect a local network service to a remote network service.
     */
    public ServiceToServiceOnlineConnection connectTo (NetworkServices networkServices, UUID networkServiceId) throws CantConnectToRemoteServiceException {

        LayerServiceToServiceOnlineConnection layerUserToUserOnlineConnection = new LayerServiceToServiceOnlineConnection(networkServices, networkServiceId);

        //layerUserToUserOnlineConnection.setCloudPlugin(mCloudPlugin);

        try
        {
            layerUserToUserOnlineConnection.connect();
        }
        catch (CantConnectToRemoteServiceException cantConnectToRemoteServiceException)
        {
            System.err.println("CantConnectToUserException: " + cantConnectToRemoteServiceException.getMessage());

            /**
             * I can't do anything with this exception here. I throw it again.
             */
            throw cantConnectToRemoteServiceException;
        }

        return layerUserToUserOnlineConnection;

    }
    
    


}
