/*
* @#RegisterServerRequestNotificationEvent.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.RegisterServerRequestNotificationEvent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 12/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RegisterServerRequestNotificationEvent extends AbstractP2PFermatEvent {


    /**
     * Represent the networkServiceTypeApplicant
     */
    private List<NetworkServiceType> listNetworkServiceTypeToReconnecting;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public RegisterServerRequestNotificationEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }


    public List<NetworkServiceType> getListNetworkServiceTypeToReconnecting() {
        return listNetworkServiceTypeToReconnecting;
    }

    public void setListNetworkServiceTypeToReconnecting(List<NetworkServiceType> listNetworkServiceTypeToReconnecting) {
        this.listNetworkServiceTypeToReconnecting = listNetworkServiceTypeToReconnecting;
    }

}
