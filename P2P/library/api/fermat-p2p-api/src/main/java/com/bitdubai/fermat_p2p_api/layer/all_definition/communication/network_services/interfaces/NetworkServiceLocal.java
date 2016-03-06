/*
 * @#NetworkServiceLocal.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal</code>
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
     * @param senderIdentityPublicKey
     * @param messageContent
     */
     void sendMessage(final String senderIdentityPublicKey, final PlatformComponentType senderType, final NetworkServiceType senderNsType, final String messageContent);

}
