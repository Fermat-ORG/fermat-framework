/*
 * @#FermatMessage.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;

import java.sql.Timestamp;
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
     * Get the receiver of the packet
     *
     * @return String
     */
    public String getReceiver();

    /**
     * Get the Sender PlatformComponentType
     *
     * @return PlatformComponentType
     */
    PlatformComponentType getSenderType();

    /**
     * Get the Receiver PlatformComponentType
     *
     * @return PlatformComponentType
     */
    PlatformComponentType getReceiverType();

    /**
     * Get the Sender NetworkServiceType
     *
     * @return NetworkServiceType
     */
    NetworkServiceType getSenderNsType();

    /**
     * Get the Receiver NetworkServiceType
     *
     * @return NetworkServiceType
     */
    NetworkServiceType getReceiverNsType();

    /**
     * Get the Content
     *
     * @return String
     */
    public String getContent();

    /**
     * Get the delivery timestamp
     *
     * @return Timestamp
     */
    public Timestamp getDeliveryTimestamp();

    /**
     * Get the Shipping Timestamp
     *
     * @return Timestamp
     */
    public Timestamp getShippingTimestamp();

    /**
     * Get the FailCount, this value indicate
     * when try to send a message an fail
     *
     * Note: This is only for outgoing message
     *
     * @return
     */
    public int getFailCount();

    /**
     * Get the FermatMessagesStatus
     *
     * @return FermatMessagesStatus
     */
    public FermatMessagesStatus getFermatMessagesStatus();

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
     * Convert to FermatPacketCommunication from json
     *
     * @param json string object
     * @return FermatMessage
     */
    public FermatMessage fromJson(String json);
}
