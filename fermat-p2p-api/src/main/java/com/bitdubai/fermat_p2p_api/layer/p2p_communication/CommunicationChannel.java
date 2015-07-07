package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.enums.RejectConnectionRequestReasons;

import java.util.Collection;

/**
 * Created by ciencias on 31.12.14.
 */
public interface CommunicationChannel {

    public OnlineChannel createOnlineChannel();
    
    public String getChannelPublicKey();
    
    public void registerNetworkService (NetworkServices networkService, String networkServicePublicKey) throws NetworkServiceNotSupportedException;
    
    public void unregisterNetworkService (NetworkServices networkService) throws NetworkServiceNotRegisteredException;
    
    public String getNetworkServiceChannelPublicKey(NetworkServices networkService) throws NetworkServiceNotRegisteredException;

    public void requestConnectiontTo(NetworkServices networkServices, String remoteNetworkService) throws CantConnectToRemoteServiceException;
    
    public Collection<String> getIncomingNetworkServiceConnectionRequests(NetworkServices networkService) throws NetworkServiceNotRegisteredException;

    public void acceptIncomingNetworkServiceConnectionRequest (NetworkServices networkService, String remoteNetworkService) throws NetworkServiceNotRegisteredException;

    public void rejectIncomingNetworkServiceConnectionRequest (NetworkServices networkService, String remoteNetworkService, RejectConnectionRequestReasons reason ) throws NetworkServiceNotRegisteredException;

    public ServiceToServiceOnlineConnection getActiveNetworkServiceConnection(NetworkServices networkService, String remoteNetworkService) throws NetworkServiceNotRegisteredException;

    public Collection<String> getActiveNetworkServiceConnectionIdentifiers(NetworkServices networkService) throws NetworkServiceNotRegisteredException;

    //TODO: JORGE El Cloud Server no deberia ser un Communication Channel
    //TODO: JORGE Sacar los metodos que no sirvan
}
