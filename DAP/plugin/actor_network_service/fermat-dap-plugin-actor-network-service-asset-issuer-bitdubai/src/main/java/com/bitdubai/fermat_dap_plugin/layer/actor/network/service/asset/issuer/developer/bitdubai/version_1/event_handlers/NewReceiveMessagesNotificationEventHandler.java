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
import com.bitdubai.fermat_dap_api.layer.all_definition.events.NewReceiveMessageActorNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.RedeemPointActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.AssetIssuerActorNetworkServicePluginRoot;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.JsonANSNamesConstants;
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
public class NewReceiveMessagesNotificationEventHandler implements FermatEventHandler {

    private EventManager eventManager;
    private AssetIssuerActorNetworkServicePluginRoot pluginRoot;


    public NewReceiveMessagesNotificationEventHandler(AssetIssuerActorNetworkServicePluginRoot assetIssuerActorNetworkServicePluginRoot, EventManager eventManager) {
//    public NewReceiveMessagesNotificationEventHandler(AssetIssuerActorNetworkServicePluginRoot assetIssuerActorNetworkServicePluginRoot) {
        this.pluginRoot = assetIssuerActorNetworkServicePluginRoot;
        this.eventManager = eventManager;
    }

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
            CharSequence pblicKeyExtended = JsonANSNamesConstants.PUBLICKEY_EXTENDED;


            if (fermatMessageReceive.getContent() != null) {

                if (fermatMessageReceive.getContent().contains(pblicKeyExtended)) {

                    ActorAssetRedeemPoint actorSender = gson.fromJson(jsonObject.get(JsonANSNamesConstants.REDEEM_POINT).getAsString(), RedeemPointActorRecord.class);
                    ActorAssetIssuer actorDestination = gson.fromJson(jsonObject.get(JsonANSNamesConstants.ISSUER).getAsString(), AssetIssuerActorRecord.class);
                    DAPMessage message = gson.fromJson(jsonObject.get(JsonANSNamesConstants.PUBLICKEY_EXTENDED).getAsString(), DAPMessage.class);

                    System.out.println("ACTOR NETWORK SERVICE ASSET ISSUER - SE LANZARA EVENTO PARA REQUEST PUBLIC KEY EXTENDED");

                    FermatEvent event = eventManager.getNewEvent(EventType.NEW_RECEIVE_MESSAGE_ACTOR);
                    event.setSource(EventSource.ACTOR_ASSET_REDEEM_POINT);
                    ((NewReceiveMessageActorNotificationEvent) event).setNewReceiveMessage(actorSender, actorDestination, message);
                    eventManager.raiseEvent(event);

                }
            }
        }
    }
}
