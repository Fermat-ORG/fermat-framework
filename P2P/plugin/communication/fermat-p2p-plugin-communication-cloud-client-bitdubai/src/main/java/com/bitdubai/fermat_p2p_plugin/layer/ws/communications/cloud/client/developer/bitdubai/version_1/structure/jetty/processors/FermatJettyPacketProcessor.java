/*
 * @#FermatPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.jetty.processors;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.WsCommunicationsCloudClientChannel;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.jetty.WsCommunicationsJettyCloudClientChannel;

/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.FermatJettyPacketProcessor</code> define
 * the methods that have to implements a fermat packet processor class, side of the client
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class FermatJettyPacketProcessor {

    /**
     * Represent the wsCommunicationsCloudClientChannel
     */
    private WsCommunicationsJettyCloudClientChannel wsCommunicationsJettyCloudClientChannel;

    /**
     * Constructor
     * @param wsCommunicationsJettyCloudClientChannel
     */
    FermatJettyPacketProcessor(WsCommunicationsJettyCloudClientChannel wsCommunicationsJettyCloudClientChannel){
        this.wsCommunicationsJettyCloudClientChannel = wsCommunicationsJettyCloudClientChannel;
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
     * Get the wsCommunicationsJettyCloudClientChannel value
     *
     * @return wsCommunicationsJettyCloudClientChannel current value
     */
    public WsCommunicationsJettyCloudClientChannel getWsCommunicationsJettyCloudClientChannel() {
        return wsCommunicationsJettyCloudClientChannel;
    }
}
