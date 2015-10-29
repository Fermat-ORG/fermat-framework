/*
* @#NewReceiveMessagesNotificationEventHandler.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.AssetUserActorNetworkServicePluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.util.JsonAssetUserANSAttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.NewCryptoAddressReceiveAssetUserActorNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.NewCryptoAddressRequestAssetUserActorNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers.NewReceiveMessagesNotificationEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 28/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewReceiveMessagesNotificationEventHandler implements FermatEventHandler {

    private EventManager eventManager;

    public NewReceiveMessagesNotificationEventHandler(AssetUserActorNetworkServicePluginRoot assetUserActorNetworkServicePluginRoot,EventManager eventManager){
        this.eventManager = eventManager;
    }

    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        System.out.print("NOTIFICACION EVENTO MENSAJE RECIBIDO TO ASSET USER!!!!");

        NewNetworkServiceMessageReceivedNotificationEvent newNetworkServiceMessageReceivedNotificationEvent = (NewNetworkServiceMessageReceivedNotificationEvent) platformEvent;
        FermatMessage fermatMessageReceive = (FermatMessage) newNetworkServiceMessageReceivedNotificationEvent.getData();

        /*
        * If is null then is RequestCryptoAddres from getSender else is new CryptoAddres delivered from remote assetUser
        */


        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(fermatMessageReceive.getContent()).getAsJsonObject();
        CharSequence contieneCryptoAddress = JsonAssetUserANSAttNamesConstants.CRYPTOADDRES;

        if(fermatMessageReceive.getContent()!=null) {

            if (!fermatMessageReceive.getContent().contains(contieneCryptoAddress)) {


                ActorAssetIssuer actorAssetIssuerSender = gson.fromJson(jsonObject.get(JsonAssetUserANSAttNamesConstants.ISSUER).getAsString(), AssetIssuerActorRecord.class);
                ActorAssetUser actorAssetUserDestination = gson.fromJson(jsonObject.get(JsonAssetUserANSAttNamesConstants.USER).getAsString(), AssetUserActorRecord.class);


                FermatEvent event = eventManager.getNewEvent(EventType.NEW_CRYPTO_ADDRESS_REQUEST_ASSET_USER);
                event.setSource(EventSource.ACTOR_ASSET_USER);
                ((NewCryptoAddressRequestAssetUserActorNotificationEvent) event).setNewCryptoAddressRequest(actorAssetIssuerSender,actorAssetUserDestination);
                eventManager.raiseEvent(event);



            } else {


                CryptoAddress cryptoAddress = gson.fromJson(jsonObject.get(JsonAssetUserANSAttNamesConstants.CRYPTOADDRES).getAsString(), CryptoAddress.class);
                ActorAssetIssuer actorAssetIssuerDestination = gson.fromJson(jsonObject.get(JsonAssetUserANSAttNamesConstants.ISSUER).getAsString(), AssetIssuerActorRecord.class);
                ActorAssetUser actorAssetUserSender = gson.fromJson(jsonObject.get(JsonAssetUserANSAttNamesConstants.USER).getAsString(), AssetUserActorRecord.class);

                FermatEvent event = eventManager.getNewEvent(EventType.NEW_CRYPTO_ADDRESS_RECEIVE_ASSET_USER);
                event.setSource(EventSource.ACTOR_ASSET_USER);
                ((NewCryptoAddressReceiveAssetUserActorNotificationEvent) event).setNewCryptoAddressReceive(actorAssetUserSender, actorAssetIssuerDestination, cryptoAddress);
                eventManager.raiseEvent(event);

            }
        }


    }
}
