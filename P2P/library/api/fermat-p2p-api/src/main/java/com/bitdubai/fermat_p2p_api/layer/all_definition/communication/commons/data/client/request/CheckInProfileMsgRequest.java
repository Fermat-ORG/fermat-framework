/*
 * @#CheckInProfileMsgRequest.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ClientProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest</code>
 * represent the message to request to register a profile with the node<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckInProfileMsgRequest extends PackageContent {

    /**
     * Represent the profileToRegister to check in
     */
    private Profile profileToRegister;

    /**
     * Constructor with parameters
     *
     * @param profileToRegister
     */
    public CheckInProfileMsgRequest(Profile profileToRegister) {
        this.profileToRegister = profileToRegister;
    }

    /**
     * Gets the value of profileToRegister and returns
     *
     * @return profileToRegister
     */
    public Profile getProfileToRegister() {
        return profileToRegister;
    }
}
