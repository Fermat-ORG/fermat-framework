/*
* @#RegisterServerRequestTyrusPacketProcessor.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.RegisterServerRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.WsCommunicationsTyrusCloudClientChannel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.RegisterServerRequestTyrusPacketProcessor</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 12/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RegisterServerRequestTyrusPacketProcessor extends FermatTyrusPacketProcessor  {

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Represent the jsonParser
     */
    private JsonParser jsonParser;

    /**
     * Constructor
     */
    public RegisterServerRequestTyrusPacketProcessor(WsCommunicationsTyrusCloudClientChannel wsCommunicationsTyrusCloudClientChannel) {
        super(wsCommunicationsTyrusCloudClientChannel);
        gson = new Gson();
        jsonParser = new JsonParser();
    }

    @Override
    public void processingPackage(FermatPacket receiveFermatPacket) {

        System.out.println("RegisterServerRequestTyrusPacketProcessor - processingPackage");

        String messageContentJsonStringRepresentation = null;

        if (getWsCommunicationsTyrusCloudClientChannel().isRegister()){

            /*
            * Get the platformComponentProfile from the message content and decrypt
            */
            //System.out.println(" CompleteRegistrationComponentTyrusPacketProcessor - decoding fermatPacket with client-identity ");
            messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsTyrusCloudClientChannel().getClientIdentity().getPrivateKey());

        }else {

            /*
            * ---------------------------------------------------------------------------------------------------
            * IMPORTANT: This Message Content of this packet come encrypted with the temporal identity public key
            * at this moment the communication cloud client is noT register
            * ---------------------------------------------------------------------------------------------------
            * Get the platformComponentProfile from the message content and decrypt
            */
            //System.out.println(" CompleteRegistrationComponentTyrusPacketProcessor - decoding fermatPacket with temp-identity ");
            messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsTyrusCloudClientChannel().getTemporalIdentity().getPrivateKey());

        }

        /*
         * Construct the json object
         */
        JsonObject contentJsonObject = jsonParser.parse(messageContentJsonStringRepresentation).getAsJsonObject();

        NetworkServiceType networkServiceType = gson.fromJson(contentJsonObject.get(JsonAttNamesConstants.NETWORK_SERVICE_TYPE).getAsString(), NetworkServiceType.class);
        String ipServer = contentJsonObject.get(JsonAttNamesConstants.IPSERVER).getAsString();

        List<NetworkServiceType> listOfnetworkService = notifyAllNetworkService(networkServiceType);

        if(listOfnetworkService != null) {

             /*
              * Create a raise a new event with the register notification to all networkservice
              */
              FermatEvent event = P2pEventType.REGISTER_SERVER_REQUEST_NOTIFICATION.getNewEvent();
              event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);

              ((RegisterServerRequestNotificationEvent) event).setListNetworkServiceTypeToReconnecting(listOfnetworkService);

             /*
              * Raise the event
              */
              System.out.println("RegisterServerRequestTyrusPacketProcessor - Raised a event = P2pEventType.REGISTER_SERVER_REQUEST_NOTIFICATION");
              getWsCommunicationsTyrusCloudClientChannel().getEventManager().raiseEvent(event);


              try {
                    getWsCommunicationsTyrusCloudClientChannel().getWsCommunicationsTyrusCloudClientConnection().
                            getWsCommunicationsCloudClientPluginRoot().connectToNewPlatformCloudServer(listOfnetworkService, networkServiceType, ipServer);
              } catch (Exception e) {
                    e.printStackTrace();
              }

        }

    }

    private List<NetworkServiceType> notifyAllNetworkService(NetworkServiceType networkServiceType){

        List<NetworkServiceType> listOfnetworkService = new ArrayList<>();

        switch (networkServiceType){

            case INTRA_USER :
                    listOfnetworkService.add(NetworkServiceType.INTRA_USER);
                    listOfnetworkService.add(NetworkServiceType.CRYPTO_ADDRESSES);
                    listOfnetworkService.add(NetworkServiceType.CRYPTO_PAYMENT_REQUEST);
                    listOfnetworkService.add(NetworkServiceType.CRYPTO_TRANSMISSION);
                break;
            case CRYPTO_BROKER :
                    listOfnetworkService.add(NetworkServiceType.CRYPTO_BROKER);
                    listOfnetworkService.add(NetworkServiceType.CRYPTO_CUSTOMER);
                    listOfnetworkService.add(NetworkServiceType.TRANSACTION_TRANSMISSION);
                    listOfnetworkService.add(NetworkServiceType.NEGOTIATION_TRANSMISSION);
                break;
            case ASSET_USER_ACTOR:
                    listOfnetworkService.add(NetworkServiceType.ASSET_USER_ACTOR);
                    listOfnetworkService.add(NetworkServiceType.ASSET_ISSUER_ACTOR);
                    listOfnetworkService.add(NetworkServiceType.ASSET_REDEEM_POINT_ACTOR);
                    listOfnetworkService.add(NetworkServiceType.ASSET_TRANSMISSION);
                break;
            case ARTIST_ACTOR:
                    listOfnetworkService.add(NetworkServiceType.ARTIST_ACTOR);
                    listOfnetworkService.add(NetworkServiceType.FAN_ACTOR);
                break;
            case CHAT:
                    listOfnetworkService.add(NetworkServiceType.CHAT);
                break;
            case FERMAT_MONITOR:
                    listOfnetworkService.add(NetworkServiceType.FERMAT_MONITOR);
                break;
            default:
                break;


        }



        return listOfnetworkService;
    }

    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.REGISTER_SERVER_REQUEST;
    }
}
