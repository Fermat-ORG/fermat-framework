/*
 * @#FermatPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationCloudServer;

import org.java_websocket.WebSocket;

/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacketProcessor</code> define
 * the method that have to implements a fermat packet processor class, side of the server
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class FermatPacketProcessor {

    /**
     * Represent the wsCommunicationCloudServer
     */
    private WsCommunicationCloudServer wsCommunicationCloudServer;

    /**
     * Get the wsCommunicationCloudServer
     * @return WsCommunicationCloudServer
     */
    protected WsCommunicationCloudServer getWsCommunicationCloudServer() {
        return wsCommunicationCloudServer;
    }

    /**
     * Set the WsCommunicationCloudServer
     *
     * @param wsCommunicationCloudServer
     */
    public void setWsCommunicationCloudServer(WsCommunicationCloudServer wsCommunicationCloudServer) {
        this.wsCommunicationCloudServer = wsCommunicationCloudServer;
    }

    /**
     * Method that contain the logic to process the packet
     */
    public abstract void processingPackage(final WebSocket clientConnection, final FermatPacket receiveFermatPacket, final ECCKeyPair serverIdentity);

    /**
     * Return the FermatPacketType that it processes
     * @return
     */
    public abstract FermatPacketType getFermatPacketType();


}
