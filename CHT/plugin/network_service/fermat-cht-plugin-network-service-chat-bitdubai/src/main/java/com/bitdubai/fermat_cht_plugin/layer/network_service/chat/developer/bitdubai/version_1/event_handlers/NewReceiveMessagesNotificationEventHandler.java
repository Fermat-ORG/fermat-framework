package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.ChatPluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatTransmissionJsonAttNames;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.processors.ChatMetadataTransmitMessageReceiverProcessor;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.processors.FermatMessageProcessor;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.processors.NewTransactionStatusNotificationMessageReceiverProcessor;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  Gabriel Araujo on 05/01/16.
 */
public class NewReceiveMessagesNotificationEventHandler implements FermatEventHandler {
    /**
     * Represent the messagesProcessorsRegistered
     */
    private Map<ChatMessageTransactionType, FermatMessageProcessor> messagesProcessorsRegistered;

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Represent the parser
     */
    private JsonParser parser;

    /**
     * Constructor with parameter
     *
     * @param
     */
    public NewReceiveMessagesNotificationEventHandler(ChatPluginRoot chatPluginRoot) {
        this.messagesProcessorsRegistered = new HashMap<>();
        this.messagesProcessorsRegistered.put(ChatMessageTransactionType.META_DATA_TRANSMIT, new ChatMetadataTransmitMessageReceiverProcessor(chatPluginRoot));
        this.messagesProcessorsRegistered.put(ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE, new NewTransactionStatusNotificationMessageReceiverProcessor(chatPluginRoot));
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

        if (platformEvent.getSource() == ChatPluginRoot.EVENT_SOURCE) {

            System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent =" + platformEvent.toString());
            System.out.print("CHAT - NOTIFICACION EVENTO MENSAJE RECIBIDO!!!!");

            /*
             * Get the message receive
             */
            NewNetworkServiceMessageReceivedNotificationEvent newNetworkServiceMessageReceivedNotificationEvent = (NewNetworkServiceMessageReceivedNotificationEvent) platformEvent;
            FermatMessage fermatMessageReceive = (FermatMessage) newNetworkServiceMessageReceivedNotificationEvent.getData();

            /*
             * Get the content of the message like a JsonObject
             */
            JsonObject jsonMsjContent = parser.parse(fermatMessageReceive.getContent()).getAsJsonObject();

            /*
             * Extract the type of content of the message
             */
            ChatMessageTransactionType chatMessageTransactionType = gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE), ChatMessageTransactionType.class);

            /*
             * Process the messages for his type
             */
            if (messagesProcessorsRegistered.containsKey(chatMessageTransactionType)) {
                messagesProcessorsRegistered.get(chatMessageTransactionType).processingMessage(fermatMessageReceive, jsonMsjContent);
            }else{
                System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - message type no supported = "+chatMessageTransactionType);
            }

        }

    }
}
