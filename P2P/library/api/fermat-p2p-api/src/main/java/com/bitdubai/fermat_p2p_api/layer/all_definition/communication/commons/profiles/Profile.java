/*
 * @#AbstractProfile.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

/**
 * The Class <code>Profile</code> is
 * the base of the component profile
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class Profile {

    /**
     * Represent the Identity public key
     */
    private String identityPublicKey;

    /**
     * Represent the location
     */
    private Location location;

    /**
     * Constructor
     */
    public Profile(){
        super();
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
     * Sets the identityPublicKey
     *
     * @param identityPublicKey to set
     */
    public void setIdentityPublicKey(String identityPublicKey) {
        this.identityPublicKey = identityPublicKey;
    }

    /**
     * Gets the value of location and returns
     *
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location
     *
     * @param location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Return this object in json string
     *
     * @return json string
     */
    public abstract String toJson();

    /**
     * Construct this object from json string
     *
     * @param string
     * @return Profile
     */
    public abstract Profile fromJson(String string);

}
