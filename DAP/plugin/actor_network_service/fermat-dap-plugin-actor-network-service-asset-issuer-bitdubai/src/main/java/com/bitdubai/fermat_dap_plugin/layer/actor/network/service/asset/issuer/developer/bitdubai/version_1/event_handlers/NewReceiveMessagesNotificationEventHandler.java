/*
* @#NewReceiveMessagesNotificationEventHandler.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.NewRequestMessageActorNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.RedeemPointActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.AssetIssuerActorNetworkServicePluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.util.JsonAssetIssuerANSAttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers.NewReceiveMessagesNotificationEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 28/10/15.
 * Modified by Franklin on 03/11/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
//public class NewReceiveMessagesNotificationEventHandler extends AbstractNewReceiveMessagesNotificationEventHandler {
public class NewReceiveMessagesNotificationEventHandler implements FermatEventHandler {

    private EventManager eventManager;
    private AssetIssuerActorNetworkServicePluginRoot pluginRoot;

//    public NewReceiveMessagesNotificationEventHandler(AbstractNetworkService assetIssuerActorNetworkServicePluginRoot){
//        super(assetIssuerActorNetworkServicePluginRoot);
//    }

    public NewReceiveMessagesNotificationEventHandler(AssetIssuerActorNetworkServicePluginRoot assetIssuerActorNetworkServicePluginRoot, EventManager eventManager) {
//    public NewReceiveMessagesNotificationEventHandler(AssetIssuerActorNetworkServicePluginRoot assetIssuerActorNetworkServicePluginRoot) {
        this.pluginRoot = assetIssuerActorNetworkServicePluginRoot;
        this.eventManager = eventManager;
    }


//    @Override
//    protected void handleNewMessages(FermatMessage message) throws FermatException {
//        ((AssetIssuerActorNetworkServicePluginRoot)networkService).handleNewMessages(message);
//    }

    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {
        // if the service is started ...
//        if (pluginRoot.getStatus() == ServiceStatus.STARTED ) {
        if (platformEvent.getSource() == AssetIssuerActorNetworkServicePluginRoot.EVENT_SOURCE) {

            System.out.println("ACTOR NETWORK SERVICE ASSET ISSUER - NOTIFICACION EVENTO MENSAJE RECIBIDO!!!!");

            NewNetworkServiceMessageReceivedNotificationEvent newNetworkServiceMessageReceivedNotificationEvent = (NewNetworkServiceMessageReceivedNotificationEvent) platformEvent;
            FermatMessage fermatMessageReceive = (FermatMessage) newNetworkServiceMessageReceivedNotificationEvent.getData();

            // if the message is destined to us.
//            if(newNetworkServiceMessageReceivedNotificationEvent.getNetworkServiceTypeApplicant() == pluginRoot.getNetworkServiceType()) {
                /*
                * If is null then is RequestCryptoAddres from getSender else is new CryptoAddres delivered from remote assetUser
                */
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(fermatMessageReceive.getContent()).getAsJsonObject();
            CharSequence pblicKeyExtended = JsonAssetIssuerANSAttNamesConstants.PUBLICKEY_EXTENDED;


            if (fermatMessageReceive.getContent() != null) {

                if (fermatMessageReceive.getContent().contains(pblicKeyExtended)) {

                    ActorAssetRedeemPoint actorRedeemPointSender = gson.fromJson(jsonObject.get(JsonAssetIssuerANSAttNamesConstants.REDEEM_POINT).getAsString(), RedeemPointActorRecord.class);
                    ActorAssetIssuer actorAssetIssuerDestination = gson.fromJson(jsonObject.get(JsonAssetIssuerANSAttNamesConstants.ISSUER).getAsString(), AssetIssuerActorRecord.class);
                    String message = gson.fromJson(jsonObject.get(JsonAssetIssuerANSAttNamesConstants.PUBLICKEY_EXTENDED).getAsString(), String.class);

                    System.out.println("Actor Asset User: SE LANZARA EVENTO PARA REQUEST PUBLIC KEY EXTENDED");

                    FermatEvent event = eventManager.getNewEvent(EventType.NEW_REQUEST_MESSAGE_ACTOR);
                    event.setSource(EventSource.ACTOR_ASSET_ISSUER);
                    ((NewRequestMessageActorNotificationEvent) event).setNewRequestMessage(actorRedeemPointSender, actorAssetIssuerDestination, message);
                    eventManager.raiseEvent(event);

                }
//                else {
//
//                    CryptoAddress cryptoAddress = gson.fromJson(jsonObject.get(JsonAssetIssuerANSAttNamesConstants.CRYPTOADDRES).getAsString(), CryptoAddress.class);
//                    ActorAssetIssuer actorAssetIssuerDestination = gson.fromJson(jsonObject.get(JsonAssetIssuerANSAttNamesConstants.ISSUER).getAsString(), AssetIssuerActorRecord.class);
//                    ActorAssetRedeemPoint actorAssetUserSender = gson.fromJson(jsonObject.get(JsonAssetIssuerANSAttNamesConstants.REDEEM_POINT).getAsString(), RedeemPointActorRecord.class);
//
//                    System.out.print("Actor Asset User: SE LANZARA EVENTO PARA RECEIVE CRYPTO ADDRESS");
//
//                    FermatEvent event = eventManager.getNewEvent(EventType.NEW_CRYPTO_ADDRESS_RECEIVE_ASSET_USER);
//                    event.setSource(EventSource.ACTOR_ASSET_USER);
//                    ((NewCryptoAddressReceiveAssetUserActorNotificationEvent) event).setNewCryptoAddressReceive(actorAssetUserSender, actorAssetIssuerDestination, cryptoAddress);
//                    eventManager.raiseEvent(event);
//                }
            }
//            }
        }
//        else {
//            System.out.print("ASSET ISSUER ACTOR NETWORK SERVICE NOT STARTED.");
//        }
    }
}
