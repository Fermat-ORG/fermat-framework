/*
 * @#FermatMessageProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.ChatPluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * The class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.processors.FermatMessageProcessor</code> define
 * the method that have to implements a fermat messages processor class, side of the server
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class FermatMessageProcessor {

    /**
     * Represent the assetTransmissionNetworkServicePluginRoot
     */
    private ChatPluginRoot chatPluginRoot;

    /**
     * Represent the gson
     */
    protected Gson gson;

    /**
     * Constructor with parameters
     * @param chatPluginRoot
     */
    public FermatMessageProcessor(ChatPluginRoot chatPluginRoot){
        super();
        this.chatPluginRoot = chatPluginRoot;
        this.gson = new Gson();
    }

    /**
     * Method that contain the logic to process the message
     */
    public abstract void processingMessage(final FermatMessage fermatMessage, final JsonObject jsonMsjContent);

    /**
     * Return the ChatMessageTransactionType that it processes
     * @return
     */
    public abstract ChatMessageTransactionType getChatMessageTransactionType();


    /**
     * Get the ChatPluginRoot
     * @return chatPluginRoot
     */
    public ChatPluginRoot getChatPluginRoot() {
        return chatPluginRoot;
    }



}
