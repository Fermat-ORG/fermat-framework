package com.bitdubai.fermat_api.layer.p2p_communication;

/**
 * Created by ciencias on 2/12/15.
 */
public interface ServiceToServiceOnlineConnection {


    
    public void reConnect() throws CantConnectToRemoteServiceException;
    
    public void disconnect();
    
    public ConnectionStatus getStatus();
    
    public void sendMessage (Message message) throws CantSendMessageException;
    
    public int getUnreadMessagesCount ();
    
    public Message readNextMessage();
    
}
