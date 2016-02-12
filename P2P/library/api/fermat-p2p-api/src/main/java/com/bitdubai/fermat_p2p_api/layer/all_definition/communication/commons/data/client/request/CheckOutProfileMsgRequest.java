/*
 * @#CheckOutProfileMsgRequest.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckOutProfileMsgRequest</code>
 * represent the message to request to unregister a profile with the node<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 29/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckOutProfileMsgRequest extends PackageContent {

    /**
     * Represent the identityPublicKey of the profile
     */
    private String identityPublicKey;

    /**
     * Constructor with parameters
     *
     * @param profileToUnRegister
     */
    public CheckOutProfileMsgRequest(Profile profileToUnRegister, String identityPublicKey) {
        this.identityPublicKey = identityPublicKey;
    }

    /**
     * Gets the value of identityPublicKey and returns
     *
     * @return identityPublicKey
     */
    public String getIdentityPublicKey() {
        return identityPublicKey;
    }
}
