/*
 * @#NewSentMessagesNotificationEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.OutgoingChat;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.ChatNetworkServicePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatTransmissionJsonAttNames;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers</code> listen
 * and handle the event <code>NewNetworkServiceMessageSentNotificationEvent</code>
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewSentMessagesNotificationEventHandler implements FermatEventHandler {

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Represent the parser
     */
    private JsonParser parser;

    /**
     * Represent the chatNetworkServicePluginRoot
     */
    private ChatNetworkServicePluginRoot chatNetworkServicePluginRoot;

    /**
     *
     * @return
     */
    public Gson getGson() {
        return gson;
    }

    /**
     *
     * @param gson
     */
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    /**
     *
     * @return
     */
    public JsonParser getParser() {
        return parser;
    }

    /**
     *
     * @param parser
     */
    public void setParser(JsonParser parser) {
        this.parser = parser;
    }

    /**
     * Constructor
     * @param chatNetworkServicePluginRoot
     */
    public NewSentMessagesNotificationEventHandler(ChatNetworkServicePluginRoot chatNetworkServicePluginRoot) {
        this.chatNetworkServicePluginRoot = chatNetworkServicePluginRoot;
        gson = new Gson();
        parser = new JsonParser();
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param platformEvent
     * @throws Exception
     */
    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        if (platformEvent.getSource() == ChatNetworkServicePluginRoot.EVENT_SOURCE){

           // System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent =" + platformEvent.toString());
          //  System.out.println("ChatNetworkServicePluginRoot - NOTIFICACION EVENTO MENSAJE ENVIADO!!!!");

            NewNetworkServiceMessageSentNotificationEvent newNetworkServiceMessageSentNotificationEvent = (NewNetworkServiceMessageSentNotificationEvent) platformEvent;
            FermatMessage fermatMessage = (FermatMessage) newNetworkServiceMessageSentNotificationEvent.getData();
            JsonObject jsonMsjContent = parser.parse(fermatMessage.getContent()).getAsJsonObject();
            UUID chatId = gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.ID_CHAT), UUID.class);
          //  System.out.println("ChatNetworkServicePluginRoot - ChatId"+chatId.toString());
            OutgoingChat event = (OutgoingChat) chatNetworkServicePluginRoot.getEventManager().getNewEvent(EventType.OUTGOING_CHAT);
            event.setChatId(chatId);
            event.setSource(ChatNetworkServicePluginRoot.EVENT_SOURCE);
            chatNetworkServicePluginRoot.getEventManager().raiseEvent(event);
           // System.out.println("ChatNetworkServicePluginRoot - OUTGOING_CHAT EVENT FIRED!:"+event);

        }

    }

}
