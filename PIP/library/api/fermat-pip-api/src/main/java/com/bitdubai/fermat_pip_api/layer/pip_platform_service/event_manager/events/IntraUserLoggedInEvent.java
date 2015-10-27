/*
 * @#IntraUserLoggedInEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.IncomingNetworkServiceConnectionRequestEvent</code> represent the event
 * when a Intra User Logged in
 * <p/>
 *
 * Created by loui on 22/02/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 07/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserLoggedInEvent extends AbstractFermatEvent {


    /**
     * Represent the intraUserId
     */
    private UUID intraUserId;

    /**
     * Constructor with parameter
     *
     * @param eventType
     */
    public IntraUserLoggedInEvent(EventType eventType){
        super(eventType);
    }


    /**
     * Constructor with parameters
     *
     * @param eventType
     * @param intraUserId
     */
    public IntraUserLoggedInEvent(EventType eventType, UUID intraUserId) {
        super(eventType);

        this.intraUserId = intraUserId;
    }



    /**
     * Get the id of the intra user logged in
     *
     * @return UUID
     */
    public UUID getIntraUserId() {
        return intraUserId;
    }

    /**
     * Set the id of the intra user logged in
     *
     * @return UUID
     */
    public void setIntraUserId(UUID intraUserId) {
        this.intraUserId = intraUserId;
    }
}
