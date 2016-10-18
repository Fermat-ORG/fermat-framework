/*
* @#ServerHandshakeRespond.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.STATUS;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ServerHandshakeRespond</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 27/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ServerHandshakeRespond extends MsgRespond {

    /**
     * Represent the identityPublicKey of the profile
     */
    private String identityPublicKey;

    /**
     * Constructor with parameters
     *
     * @param status
     * @param details
     * @param identityPublicKey
     */
    public ServerHandshakeRespond(UUID packageId, STATUS status, String details, String identityPublicKey) {
        super(packageId,status, details);
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

    /**
     * Generate the json representation
     * @return String
     */
    @Override
    public String toJson() {
        return GsonProvider.getGson().toJson(this, getClass());
    }

    /**
     * Get the object
     *
     * @param content
     * @return PackageContent
     */
    public static ServerHandshakeRespond parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, ServerHandshakeRespond.class);
    }

}
