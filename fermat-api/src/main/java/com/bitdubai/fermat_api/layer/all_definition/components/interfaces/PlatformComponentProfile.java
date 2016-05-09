/*
 * @#PlatformComponentProfile.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.components.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile</code> represent
 * the profile of the platform component, that is use to register like a platform component whit the web socket communication cloud server.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface PlatformComponentProfile extends Cloneable {

    /**
     * Return the public key that represent the identity of the component
     *
     * @return String
     */
    String getIdentityPublicKey();

    /**
     * Get the alias
     *
     * @return String
     */
    String getAlias();

    /**
     * Get the name
     *
     * @return String
     */
    String getName();

    /**
     * Get the location for geo localization
     *
     * @return Double
     */
    Location getLocation();

    /**
     * Return the platform component type
     *
     * @return PlatformComponentType
     */
    PlatformComponentType getPlatformComponentType();

    /**
     * Get the network service type of the packet
     *
     * @return NetworkServiceType
     */
    NetworkServiceType getNetworkServiceType();

    /**
     * Return the public key that represent the identity of the Web Socket Communication Cloud Client,
     * that this component use like communication channel
     *
     * @return String
     */
    String getCommunicationCloudClientIdentity();

    /**
     * Return the extra data
     *
     * @return String
     */
    String getExtraData();

    /**
     * Convert this object to json string
     *
     * @return String json
     */
    String toJson();

    /**
     * Convert to PlatformComponentProfile from json
     *
     * @param json string object
     * @return PlatformComponentProfile
     */
    PlatformComponentProfile fromJson(String json);

}
