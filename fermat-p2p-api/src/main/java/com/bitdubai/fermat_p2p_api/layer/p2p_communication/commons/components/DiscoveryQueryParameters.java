/*
 * @#DiscoveryQueryParameters.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.PlatformComponentType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.DiscoveryQueryParameters</code> this if use to pass
 * the parameters whit the communication cloud server search on all register PlatformComponentProfile to match whit this params
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 16/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DiscoveryQueryParameters {

    /**
     * Return the public key that represent the identity of the component
     *
     * @return String
     */
    public String getIdentityPublicKey();

    /**
     * Get the alias
     *
     * @return String
     */
    public String getAlias();

    /**
     * Get the name
     *
     * @return String
     */
    public String getName();

    /**
     * Get the latitude for geo localization
     *
     * @return Double
     */
    public Double getLatitude();

    /**
     * Get the longitude for geo localization
     *
     * @return Double
     */
    public Double getLongitude();

    /**
     * Return the platform component type
     *
     * @return PlatformComponentType
     */
    public PlatformComponentType getPlatformComponentType();

    /**
     * Get the network service type of the packet
     *
     * @return NetworkServiceType
     */
    public NetworkServiceType getNetworkServiceType();

    /**
     * Return the extra data
     *
     * @return String
     */
    public String getExtraData();

    /**
     * Get the first Record to make pagination
     *
     * @return int
     */
    public Integer firstRecord();

    /**
     * Get the number of register to return
     *
     * @return int
     */
    public Integer getNumberRegister();

    /**
     * Convert this object to json string
     *
     * @return String json
     */
    public String toJson();

    /**
     * Convert to DiscoveryQueryParameters from json
     *
     * @param json string object
     * @return DiscoveryQueryParameters
     */
    public DiscoveryQueryParameters fromJson(String json);

}
