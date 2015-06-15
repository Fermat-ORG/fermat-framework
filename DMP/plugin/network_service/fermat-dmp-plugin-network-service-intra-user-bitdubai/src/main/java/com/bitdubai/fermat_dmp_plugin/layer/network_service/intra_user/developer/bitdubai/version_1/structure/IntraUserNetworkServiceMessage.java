/*
 * @#IntraUserNetworkServiceMessage.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._10_communication.Message;
import com.bitdubai.fermat_api.layer._10_communication.MessagesStatus;

import java.io.Serializable;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceMessage</code>
 * is the implementation of the message<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserNetworkServiceMessage implements Message, Serializable {

    /**
     * Represent the serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represent  the textContent
     */
    private String textContent;

    /**
     * Represent the status
     */
    private MessagesStatus status;

    /**
     * Represent the signature
     */
    private String signature;

    /**
     * Constructor
     */
    public IntraUserNetworkServiceMessage(){
        super();
    }

    /**
     * Constructor whit parameter
     */
    public IntraUserNetworkServiceMessage(String signature, MessagesStatus status, String textContent) {
        this.signature = signature;
        this.status = status;
        this.textContent = textContent;
    }

    /**
     * Return the signature
     *
     * @return String
     */
    public String getSignature() {
        return signature;
    }

    /**
     *  Set the signature
     *
     * @param signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * (non-Javadoc)
     *
     * @see Message#getStatus()
     */
    @Override
    public MessagesStatus getStatus() {
        return status;
    }

    public void setStatus(MessagesStatus status) {
        this.status = status;
    }

    /**
     * (non-Javadoc)
     *
     * @see Message#getTextContent()
     */
    @Override
    public String getTextContent() {
        return textContent;
    }

    /**
     * (non-Javadoc)
     *
     * @see Message#setTextContent(String)
     */
    @Override
    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

}
