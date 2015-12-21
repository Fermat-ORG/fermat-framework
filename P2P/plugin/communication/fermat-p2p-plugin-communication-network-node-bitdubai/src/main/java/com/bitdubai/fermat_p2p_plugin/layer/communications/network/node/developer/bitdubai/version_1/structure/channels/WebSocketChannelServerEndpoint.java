/*
 * @#WebSocketChannelServerEndpoint.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Message;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.MessageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketChannelServerEndpoint</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class WebSocketChannelServerEndpoint {

    /**
     * Represent the list of messages processors
     */
    private Map<MessageType, List<MessageProcessor>> messagesProcessors;

    /**
     * Represent the channelIdentity
     */
    private ECCKeyPair channelIdentity;

    /**
     * Constructor
     */
    public WebSocketChannelServerEndpoint(){
        super();
        this.messagesProcessors = new HashMap<>();
    }

    /**
     * This method register a MessageProcessor object with this
     * channel
     */
    public void registerMessageProcessor(MessageProcessor messageProcessor) {

        /*
         * Set server reference
         */

        //Validate if a previous list created
        if (messagesProcessors.containsKey(messageProcessor.getMessageType())){

            /*
             * Add to the existing list
             */
            messagesProcessors.get(messageProcessor.getMessageType()).add(messageProcessor);

        }else{

            /*
             * Create a new list
             */
            List<MessageProcessor> messageProcessorList = new ArrayList<>();
            messageProcessorList.add(messageProcessor);

            /*
             * Add to the messageProcessor
             */
            messagesProcessors.put(messageProcessor.getMessageType(), messageProcessorList);
        }

    }

    /**
     * Gets the value of channelIdentity and returns
     *
     * @return channelIdentity
     */
    public ECCKeyPair getChannelIdentity() {
        return channelIdentity;
    }

    /**
     * Sets the channelIdentity
     *
     * @param channelIdentity to set
     */
    void setChannelIdentity(ECCKeyPair channelIdentity) {
        this.channelIdentity = channelIdentity;
    }

    /**
     * Gets the value of messagesProcessors and returns
     *
     * @return messagesProcessors
     */
    protected Map<MessageType, List<MessageProcessor>> getMessagesProcessors() {
        return messagesProcessors;
    }

    /**
     * Validate if can process the message type
     *
     * @param messageType to process
     * @return true or false
     */
    protected boolean canProcessMessage(MessageType messageType){
        return messagesProcessors.containsKey(messageType);
    }

    /**
     * Method that process a new message received
     *
     * @param message
     * @param session
     */
    protected void processMessage(Message message, Session session) throws IllegalArgumentException{

        /*
         * Validate if can process the message
         */
        if (canProcessMessage(message.getMessageType())){

            /*
             * Get list of the processor
             */
            for (MessageProcessor messageProcessor: messagesProcessors.get(message.getMessageType())) {

                /*
                 * Process the message
                 */
                messageProcessor.processingMessage(session, message);
            }

        }else {

            throw new IllegalArgumentException("The message type: "+message.getMessageType()+" is not supported");
        }
    }

    /**
     * Initialize the message processor
     */
    abstract void initMessageProcessors();

}
