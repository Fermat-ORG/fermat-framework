/*
 * @#RespondMsg.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.RespondMsg</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RespondMsg extends PackageContent {

    /**
     * Represent the status of the check in process
     */
    public enum STATUS{
        SUCCESS,
        FAIL
    }

    /**
     * Represent the status
     */
    private STATUS status;

    /**
     * Represent the details
     */
    private String details;

    /**
     * Constructor with parameters
     *
     * @param status
     * @param details
     */
    public RespondMsg(STATUS status, String details){
        this.status = status;
        this.details = details;
    }

    /**
     * Gets the value of status and returns
     *
     * @return status
     */
    public STATUS getStatus() {
        return status;
    }
}
