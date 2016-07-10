package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;

import org.apache.commons.lang.NotImplementedException;

import java.io.Serializable;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class PackageContent implements Serializable{

    /**
     * Represent the fermatMessageContentType
     */
    private MessageContentType messageContentType;

    /**
     * Constructor
     */
    public PackageContent() {
        super();
        this.messageContentType = MessageContentType.OBJECT;
    }

    /**
     * Constructor whit parameters
     * @param messageContentType
     */
    public PackageContent(MessageContentType messageContentType) {
        this.messageContentType = messageContentType;
    }

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

    /**
     * Generate the json representation
     * @return String
     */

    public String toJson() {
        throw  new NotImplementedException();
    }

    /**
     * Get the object
     *
     * @param content
     * @return PackageContent
     */
    public static PackageContent parseContent(String content) {
        throw  new NotImplementedException();
    }
}
