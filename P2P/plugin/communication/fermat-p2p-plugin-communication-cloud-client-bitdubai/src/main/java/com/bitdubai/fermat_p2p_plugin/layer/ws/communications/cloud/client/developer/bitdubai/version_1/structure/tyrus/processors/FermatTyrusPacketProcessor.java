/*
 * @#FermatPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.WsCommunicationsTyrusCloudClientChannel;

/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.FermatTyrusPacketProcessor</code> define
 * the methods that have to implements a fermat packet processor class, side of the client
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class FermatTyrusPacketProcessor {

    /**
     * Represent the wsCommunicationsCloudClientChannel
     */
    private WsCommunicationsTyrusCloudClientChannel wsCommunicationsTyrusCloudClientChannel;

    /**
     * Constructor
     * @param wsCommunicationsTyrusCloudClientChannel
     */
    FermatTyrusPacketProcessor(WsCommunicationsTyrusCloudClientChannel wsCommunicationsTyrusCloudClientChannel){
        this.wsCommunicationsTyrusCloudClientChannel = wsCommunicationsTyrusCloudClientChannel;
    }

    /**
     * Method that contain the logic to process the packet
     */
    public abstract void processingPackage(final FermatPacket receiveFermatPacket);

    /**
     * Return the FermatPacketType that it processes
     *
     * @return FermatPacketType
     */
    public abstract FermatPacketType getFermatPacketType();


    /**
     * Get the wsCommunicationsTyrusCloudClientChannel value
     *
     * @return wsCommunicationsTyrusCloudClientChannel current value
     */
    public WsCommunicationsTyrusCloudClientChannel getWsCommunicationsTyrusCloudClientChannel() {
        return wsCommunicationsTyrusCloudClientChannel;
    }
}
