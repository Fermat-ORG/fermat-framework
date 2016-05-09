package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.google.gson.Gson;

import org.apache.commons.lang.NotImplementedException;

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
     * Constructor
     */
    public PackageContent() {
        super();
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

    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this, getClass());
    }

    public static PackageContent parseContent(String content) {

        throw new NotImplementedException("You must override this method.");
    }
}
