/*
 * @#NewReceiveMessagesNotificationEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.enums.DigitalAssetMetadataTransactionType;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.AssetTransmissionNetworkServicePluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.AssetTransmissionJsonAttNames;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.processors.DigitalAssetMetadataTransmitMessageReceiverProcessor;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.processors.FermatMessageProcessor;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.processors.NewTransactionStatusNotificationMessageReceiverProcessor;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

/**
 * The class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.event_handlers.NewSentMessagesNotificationEventHandler</code> listen
 * and handle the event <code>NewNetworkServiceMessageReceivedNotificationEvent</code>
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewReceiveMessagesNotificationEventHandler implements FermatEventHandler {

    /**
     * Represent the messagesProcessorsRegistered
     */
    private Map<DigitalAssetMetadataTransactionType, FermatMessageProcessor> messagesProcessorsRegistered;

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
    public NewReceiveMessagesNotificationEventHandler(AssetTransmissionNetworkServicePluginRoot assetTransmissionNetworkServicePluginRoot) {
        this.messagesProcessorsRegistered = new HashMap<>();
        this.messagesProcessorsRegistered.put(DigitalAssetMetadataTransactionType.META_DATA_TRANSMIT, new DigitalAssetMetadataTransmitMessageReceiverProcessor(assetTransmissionNetworkServicePluginRoot));
        this.messagesProcessorsRegistered.put(DigitalAssetMetadataTransactionType.TRANSACTION_STATUS_UPDATE, new NewTransactionStatusNotificationMessageReceiverProcessor(assetTransmissionNetworkServicePluginRoot));
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

        if (platformEvent.getSource() == AssetTransmissionNetworkServicePluginRoot.EVENT_SOURCE) {

            System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent =" + platformEvent.toString());
            System.out.print("NOTIFICACION EVENTO MENSAJE RECIVIDO!!!!");

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
            DigitalAssetMetadataTransactionType digitalAssetMetadataTransactionType = gson.fromJson(jsonMsjContent.get(AssetTransmissionJsonAttNames.MSJ_CONTENT_TYPE), DigitalAssetMetadataTransactionType.class);

            /*
             * Process the messages for his type
             */
            if (messagesProcessorsRegistered.containsKey(digitalAssetMetadataTransactionType)) {
                messagesProcessorsRegistered.get(digitalAssetMetadataTransactionType).processingMessage(fermatMessageReceive, jsonMsjContent);
            }else{
                System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - message type no supported = "+digitalAssetMetadataTransactionType);
            }

        }

    }

}
