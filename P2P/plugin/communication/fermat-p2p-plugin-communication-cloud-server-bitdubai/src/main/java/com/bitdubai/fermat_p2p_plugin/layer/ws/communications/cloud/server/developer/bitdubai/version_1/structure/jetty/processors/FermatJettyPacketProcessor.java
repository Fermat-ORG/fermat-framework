/*
 * @#FermatJettyPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.ClientConnection;


/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatJettyPacketProcessor</code> define
 * the method that have to implements a fermat packet processor class, side of the server
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class FermatJettyPacketProcessor {

    /**
     * Method that contain the logic to process the packet
     */
    public abstract void processingPackage(final ClientConnection clientConnection, final FermatPacket receiveFermatPacket);

    /**
     * Return the FermatPacketType that it processes
     * @return
     */
    public abstract FermatPacketType getFermatPacketType();


}
