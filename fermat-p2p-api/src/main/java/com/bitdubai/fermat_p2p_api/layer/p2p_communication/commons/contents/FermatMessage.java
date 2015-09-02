/*
 * @#FermatMessage.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;


import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 01/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatMessage {

    /**
     * Get the id of the package
     */
    public UUID getId();

    /**
     * Get the sender of the packet
     *
     * @return String
     */
    public String getSender();

    /**
     * Get the destination of the packet
     *
     * @return String
     */
    public String getDestination();

    /**
     * Get the Content
     *
     * @return byte[]
     */
    public byte[] getContent();

    /**
     * Get the signature of the packet
     *
     * @return String
     */
    public String getSignature();

    /**
     * Get the Fermat Message Content Type
     *
     * @return NetworkServiceType
     */
    public FermatMessageContentType getFermatMessageContentType();

    /**
     * Convert this object to json string
     *
     * @return String json
     */
    public String toJson();

    /**
     * Convert to CommunicationFermatPacket from json
     *
     * @param json string object
     * @return FermatMessage
     */
    public FermatMessage fromJson(String json);
}
