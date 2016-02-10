/*
 * @#FermatMessageProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.NetworkServiceChatNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * The class <code>com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.processor.FermatMessageProcessor</code> define
 * the method that have to implements a fermat messages processor class, side of the server
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/10/15.
 * Implemented by Gabriel Araujo to CHT
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class FermatMessageProcessor {

    /**
     * Represent the NetworkServiceChatNetworkServicePluginRoot
     */
    private NetworkServiceChatNetworkServicePluginRoot networkServiceChatNetworkServicePluginRoot;

    /**
     * Represent the gson
     */
    protected Gson gson;

    /**
     * Constructor with parameters
     * @param networkServiceChatNetworkServicePluginRoot
     */
    public FermatMessageProcessor(NetworkServiceChatNetworkServicePluginRoot networkServiceChatNetworkServicePluginRoot){
        super();
        this.networkServiceChatNetworkServicePluginRoot = networkServiceChatNetworkServicePluginRoot;
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
     * Get the NetworkServiceChatNetworkServicePluginRoot
     * @return networkServiceChatNetworkServicePluginRoot
     */
    public NetworkServiceChatNetworkServicePluginRoot getNetworkServiceChatNetworkServicePluginRoot() {
        return networkServiceChatNetworkServicePluginRoot;
    }



}
