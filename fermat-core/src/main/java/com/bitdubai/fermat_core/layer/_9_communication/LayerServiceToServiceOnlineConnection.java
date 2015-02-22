package com.bitdubai.fermat_core.layer._9_communication;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer._10_network_service.NetworkService;
import com.bitdubai.fermat_api.layer._10_network_service.intra_user.IntraUser;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._4_user.DeviceUser;
import com.bitdubai.fermat_api.layer._9_communication.CantConnectToRemoteServiceException;
import com.bitdubai.fermat_api.layer._9_communication.CommunicationChannel;
import com.bitdubai.fermat_api.layer._9_communication.OnlineChannel;
import com.bitdubai.fermat_api.layer._9_communication.ServiceToServiceOnlineConnection;

import java.util.UUID;

/**
 * Created by ciencias on 2/12/15.
 */


/**
 * This objects wraps the user to user online connection provided by the plugin. The purpose of doing this is to handle
 * the disconnection of the remote user to the channel the plugin is working on. 
 * 
 * In case of detecting such disconnection this object will try to reconnect through the same channel or other alternatives
 * available. 
 */

public class LayerServiceToServiceOnlineConnection implements ServiceToServiceOnlineConnection, DealsWithEvents{
    
    
    Plugin cloudPlugin;



    NetworkService localNetworkService;
    UUID remoteNetworkService;
    
    
    public LayerServiceToServiceOnlineConnection(NetworkServices localNetworkServices, UUID remoteNetworkService) {
        
        
    }



    @Override
    public void connect() throws CantConnectToRemoteServiceException {

        /**
         * There are several ways to establish an online connection implemented by different plugins. It is also 
         * possible to  try several of these ways until we find a connection to the desired user.
         */
        
        /**
         * As of today, there is only one way possible to connect to other users, and it is via the Cloud connection
         * channel.
         */

        OnlineChannel onlineChannel = ((CommunicationChannel) cloudPlugin).createOnlineChannel();
        ServiceToServiceOnlineConnection serviceToServiceOnlineConnection =  onlineChannel.createOnlineConnection(this.localNetworkService, this.remoteNetworkService);

        try
        {
            serviceToServiceOnlineConnection.connect();
        }
        catch (CantConnectToRemoteServiceException cantConnectToRemoteServiceException)
        {
            System.err.println("CantConnectToUserException: " + cantConnectToRemoteServiceException.getMessage());

            /**
             * Since this is the only implementation of a communication channel if the connection cannot be established
             * then there is no other option but to throw the exception again.
             */
            throw cantConnectToRemoteServiceException;
        }


    }

    @Override
    public void disconnect() {

    }

    @Override
    public void setEventManager(EventManager eventManager) {
        
    }



    
}
