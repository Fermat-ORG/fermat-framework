/*
 * @#NewNetworkServiceMessageReceivedEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.interfaces.CommunicationBaseEvent;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent</code> represent the event
 * when a new Network Service Message is received
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewNetworkServiceMessageReceivedNotificationEvent extends AbstractP2PFermatEvent implements CommunicationBaseEvent {

    /**
     *  Represent the data
     */
    private Object data;

    /**
     *  Applicant network service
     */
    private NetworkServiceType networkServiceTypeApplicant;

    /**
     * Constructor with parameter
     *
     * @param p2pEventType type of the event
     */
    public NewNetworkServiceMessageReceivedNotificationEvent(P2pEventType p2pEventType){
        super(p2pEventType);
    }

    /**
     * Return the data object that contains message received
     *
     * @return message received
     */
    public Object getData() {
        return data;
    }

    /**
     * Set data object that contains the message received
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Get the NetworkServiceTypeApplicant
     * @return NetworkServiceTypeApplicant
     */
    public NetworkServiceType getNetworkServiceTypeApplicant() {
        return networkServiceTypeApplicant;
    }

    public void setNetworkServiceTypeApplicant(NetworkServiceType networkServiceTypeApplicant) {
        this.networkServiceTypeApplicant = networkServiceTypeApplicant;
    }
}
