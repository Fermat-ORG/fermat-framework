package com.bitdubai.fermat_api.layer._9_communication;

/**
 * Created by ciencias on 2/23/15.
 */
public interface Message {

    public void setTextContent (String content);
    
    public String getTextContent ();

    public MessagesStatus getStatus();
    
}
