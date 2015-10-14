/*
 * @#FermatPacket.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket</code> represent
 * the package protocol to transport the data
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 01/09/15.
 *
 * @version 1.0
 */
public interface FermatPacket {

    /**
     * Represent the PACKET_MAX_BYTE_SIZE = 1024
     */
	public static final int PACKET_MAX_BYTE_SIZE = 1024;

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
     * Get the type of the packet
     *
     * @return FermatPacketType
     */
	public FermatPacketType getFermatPacketType();

    /**
     * Get the message content of the packet
     *
     * @return String
     */
	public String getMessageContent();

    /**
     * Get the signature of the packet
     *
     * @return String
     */
	public String getSignature();

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
    public FermatPacket fromJson(String json);

}
