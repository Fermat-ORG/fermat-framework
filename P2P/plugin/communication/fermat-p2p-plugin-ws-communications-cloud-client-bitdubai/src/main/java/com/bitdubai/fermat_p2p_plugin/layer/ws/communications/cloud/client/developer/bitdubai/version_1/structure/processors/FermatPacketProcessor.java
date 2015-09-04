/*
 * @#FermatPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.WsCommunicationsCloudClient;

/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.FermatPacketProcessor</code> define
 * the methods that have to implements a fermat packet processor class, side of the client
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class FermatPacketProcessor {

    /**
     * Represent the wsCommunicationsCloudClient
     */
    private WsCommunicationsCloudClient wsCommunicationsCloudClient;

    /**
     * Get the WsCommunicationsCloudClient
     *
     * @return WsCommunicationsCloudClient
     */
    protected WsCommunicationsCloudClient getWsCommunicationsCloudClient() {
        return wsCommunicationsCloudClient;
    }

    /**
     * Set the WsCommunicationsCloudClient
     * @param wsCommunicationsCloudClient
     */
    public void setWsCommunicationsCloudClient(WsCommunicationsCloudClient wsCommunicationsCloudClient) {
        this.wsCommunicationsCloudClient = wsCommunicationsCloudClient;
    }

    /**
     * Method that contain the logic to process the packet
     */
    public abstract void processingPackage(FermatPacket fermatPacket);

    /**
     * Return the FermatPacketType that it processes
     *
     * @return FermatPacketType
     */
    public abstract FermatPacketType getFermatPacketType();


}
