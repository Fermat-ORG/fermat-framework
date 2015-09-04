/*
 * @#PlatformComponentProfile.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.PlatformComponentType;

import java.net.InetAddress;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.PlatformComponentProfile</code> represent
 * the profile of the platform component, that is use to register like a platform component whit the web socket communication cloud server.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface PlatformComponentProfile {

    /**
     * Return the public key that represent the identity of the component
     *
     * @return String
     */
    public String getIdentity();

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
     * @return String
     */
    public String getLatitude();

    /**
     * Get the longitude for geo localization
     *
     * @return String
     */
    public String getLongitude();

    /**
     * Get the InetAddress
     *
     * @return InetAddress
     */
    public InetAddress getInetAddress();

    /**
     * Return the platform component type
     *
     * @return PlatformComponentType
     */
    public PlatformComponentType getComponentType();

    /**
     * Get the network service type of the packet
     *
     * @return NetworkServiceType
     */
    public NetworkServiceType getNetworkServiceType();

    /**
     * Return the public key that represent the identity of the Web Socket Communication Cloud Client,
     * that this component use like communication channel
     *
     * @return String
     */
    public String getCommunicationCloudClientIdentity();

    /**
     * Convert this object to json string
     *
     * @return String json
     */
    public String toJson();

    /**
     * Convert to FermatPacketCommunication from json
     *
     * @param json string object
     * @return FermatPacket
     */
    public PlatformComponentProfile fromJson(String json);

}
