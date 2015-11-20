/*
 * @#IntraUser.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces;


/**
 * The interface <code>com.bitdubai.fermat_api.layer.network_service.intra_user.Template</code>
 * expose the basic method of a Network Intra User
 * <p/>
 *
 * Created by ciencias on 2/13/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface IntraUser {


    /**
     *
     * @return
     */
    public String getPublicKey();

    /**
     *
     * @return
     */
    public byte[]  getProfileImage();

    /**
     * Return the userName
     *
     * @return String
     */
    public String getName ();

}
