/*
 * @#RespondProfileCheckInMsj.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.RespondProfileCheckInMsj</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RespondProfileCheckInMsj extends PackageContent {

    /**
     * Represent the status of the check in process
     */
    public enum STATUS{
        SUCCESS,
        FAIL
    }

    /**
     * Represent the identityPublicKey of the profile
     */
    private String identityPublicKey;

    /**
     * Represent the status
     */
    private STATUS status;

    /**
     * Constructor with parameters
     *
     * @param identityPublicKey
     * @param status
     */
    public RespondProfileCheckInMsj(String identityPublicKey, STATUS status) {
        this.identityPublicKey = identityPublicKey;
        this.status = status;
    }

    /**
     * Gets the value of identityPublicKey and returns
     *
     * @return identityPublicKey
     */
    public String getIdentityPublicKey() {
        return identityPublicKey;
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
