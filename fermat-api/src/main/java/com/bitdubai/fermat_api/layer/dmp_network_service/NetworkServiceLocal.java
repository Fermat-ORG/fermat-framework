/*
 * @#NetworkServiceLocal.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.dmp_network_service;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_network_service.NetworkServiceLocal</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 28/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface NetworkServiceLocal {

    /**
     * This method prepare the message to send and save on the
     * data base in the table <code>outgoing_messages</code>
     *
     * @param messageContent the message to send
     */
    public void sendMessage(final String messageContent, final ECCKeyPair senderIdentity);


    //public FermatMessage getLastMessageReceived();
}
