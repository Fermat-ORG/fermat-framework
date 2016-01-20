/*
 * @#MessageContent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class PackageContent {

    /**
     * Represent the fermatMessageContentType
     */
    private MessageContentType messageContentType;

    /**
     * Gets the value of messageContentType and returns
     *
     * @return messageContentType
     */
    public MessageContentType getMessageContentType() {
        return messageContentType;
    }

    /**
     * Sets the messageContentType
     *
     * @param messageContentType to set
     */
    public void setMessageContentType(MessageContentType messageContentType) {
        this.messageContentType = messageContentType;
    }
}
