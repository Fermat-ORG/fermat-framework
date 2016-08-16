/*
* @#MessageTransmitRespondProcessor.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageDeliveredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageFailedEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.MessageTransmitRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection;

import javax.websocket.Session;

/**
 *
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MessageTransmitSyncACKProcessor extends PackageProcessor{

    private NetworkClientCommunicationConnection networkClientCommunicationConnection;


    /**
     * Constructor whit parameter
     *
     * @param networkClientCommunicationChannel register
     */
    public MessageTransmitSyncACKProcessor(final NetworkClientCommunicationChannel networkClientCommunicationChannel,NetworkClientCommunicationConnection networkClientCommunicationConnection) {
        super(
                networkClientCommunicationChannel,
                PackageType.MESSAGE_TRANSMIT_SYNC_ACK_RESPONSE
        );
        this.networkClientCommunicationConnection = networkClientCommunicationConnection;
    }


    @Override
    public void processingPackage(Session session,Package packageReceived) {

        System.out.println("Processing new package received, packageType: "+packageReceived.getPackageType());
        MessageTransmitRespond messageTransmitRespond = MessageTransmitRespond.parseContent(packageReceived.getContent());

        System.out.println("MessageTrasmitRespondProcessor: "+ messageTransmitRespond.toJson());

        if(messageTransmitRespond.getStatus() == MessageTransmitRespond.STATUS.SUCCESS){
            /*
             * Raise the event
             */
            System.out.println("MessageTransmitRespondProcessor send sucess---SYNC!");
            networkClientCommunicationConnection.receiveSyncPackgageMessage(messageTransmitRespond.getMessageId().toString(), true);

        }else{
            System.out.println("MessageTransmitRespondProcessor send sucess---SYNC!");
            networkClientCommunicationConnection.receiveSyncPackgageMessage(messageTransmitRespond.getMessageId().toString(), false);
        }

    }

}
