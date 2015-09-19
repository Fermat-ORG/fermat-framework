package com.bitdubai.fermat_core.layer.p2p_communication;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.*;

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

public class LayerServiceToServiceOnlineConnection implements ServiceToServiceOnlineConnection{
    
    
    Plugin cloudPlugin;
    Plugin p2pPlugin;

    ServiceToServiceOnlineConnection serviceToServiceOnlineConnection;

    NetworkServices networkService;
    UUID remoteNetworkService;
    
    
    public LayerServiceToServiceOnlineConnection(NetworkServices networkService, UUID remoteNetworkService) {
        
        this.networkService = networkService;
        this.remoteNetworkService = remoteNetworkService;
        
    }

    public void setCloudPlugin (Plugin cloudPlugin){
        this.cloudPlugin = cloudPlugin;
    }

    public void setP2pPlugin (Plugin p2pPlugin){
        this.p2pPlugin = cloudPlugin;
    }

    
    public void connect() throws CantConnectToRemoteServiceException {
        
        try
        {
            connectByAnyChannel();
        }
        catch (CantConnectToRemoteServiceException cantConnectToRemoteServiceException)
        {
            System.err.println("CantConnectToRemoteServiceException: " + cantConnectToRemoteServiceException.getMessage());
            
            throw cantConnectToRemoteServiceException;
        }
        
    }

    @Override
    public void reConnect() throws CantConnectToRemoteServiceException {
        
        this.serviceToServiceOnlineConnection.disconnect();

        try
        {
            connectByAnyChannel();
        }
        catch (CantConnectToRemoteServiceException cantConnectToRemoteServiceException)
        {
            System.err.println("CantConnectToRemoteServiceException: " + cantConnectToRemoteServiceException.getMessage());

            throw cantConnectToRemoteServiceException;
        }
    }

    @Override
    public void disconnect() {
        this.serviceToServiceOnlineConnection.disconnect();
    }

    @Override
    public ConnectionStatus getStatus() {
        return this.serviceToServiceOnlineConnection.getStatus();
    }

    @Override
    public void sendMessage(Message message) throws CantSendMessageException {
        this.serviceToServiceOnlineConnection.sendMessage(message);
        // Luis: TODO verificar la excepcion que puede devoler y si es un problema de desconexion actuar en concecuencia.
    }

    @Override
    public void clearMessage(Message message) {

    }

    @Override
    public int getUnreadMessagesCount() {
        return this.serviceToServiceOnlineConnection.getUnreadMessagesCount();
    }

    @Override
    public Message readNextMessage() {
        return this.serviceToServiceOnlineConnection.readNextMessage();
    }



    private void connectByAnyChannel() throws CantConnectToRemoteServiceException {

        /**
         * There are several ways to establish an online connection implemented by different plugins. It is also 
         * possible to  try several of these ways until we find a connection to the desired service.
         */

        /**
         * As of today, there is only one way possible to connect to other services, and it is via the Cloud connection
         * channel.
         */

        OnlineChannel onlineChannel = ((CommunicationChannel) cloudPlugin).createOnlineChannel();

        try
        {
            this.serviceToServiceOnlineConnection =  onlineChannel.connectTo(this.networkService, this.remoteNetworkService);
        }
        catch (CantConnectToRemoteServiceException cantConnectToRemoteServiceException)
        {
            System.err.println("CantConnectToRemoteServiceException: " + cantConnectToRemoteServiceException.getMessage());

            /**
             * Since this is the only implementation of a communication channel if the connection cannot be established
             * then there is no other option but to throw the exception again.
             */
            throw cantConnectToRemoteServiceException;
        }
    }

    
}
