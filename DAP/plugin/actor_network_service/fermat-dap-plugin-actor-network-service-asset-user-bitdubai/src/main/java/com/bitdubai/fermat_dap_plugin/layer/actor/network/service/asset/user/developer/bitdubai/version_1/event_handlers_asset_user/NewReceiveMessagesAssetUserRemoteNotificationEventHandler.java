/*
* @#NewReceiveMessagesAssetUserRemoteNotificationEventHandler.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers_asset_user;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorNetworkServiceAssetUser;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.Gson;


/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers_asset_user.NewReceiveMessagesAssetUserRemoteNotificationEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 16/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewReceiveMessagesAssetUserRemoteNotificationEventHandler implements FermatEventHandler {



    private ActorNetworkServiceAssetUser actorNetworkServiceAssetUser;


    public NewReceiveMessagesAssetUserRemoteNotificationEventHandler(ActorNetworkServiceAssetUser actorNetworkServiceAssetUser){

        this.actorNetworkServiceAssetUser=actorNetworkServiceAssetUser;


    }



    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        System.out.print("NOTIFICACION EVENTO MENSAJE RECIBIDO!!!!");

        NewNetworkServiceMessageReceivedNotificationEvent newNetworkServiceMessageReceivedNotificationEvent = (NewNetworkServiceMessageReceivedNotificationEvent) platformEvent;
        FermatMessage fermatMessageReceive = (FermatMessage) newNetworkServiceMessageReceivedNotificationEvent.getData();

        /*
        * If is null then is RequestCryptoAddres from getSender else is new CryptoAddres delivered from remote assetUser
        */
        if(fermatMessageReceive.getContent() == null || fermatMessageReceive.getContent() == "null" || fermatMessageReceive.getContent().equalsIgnoreCase("NULL")
                || fermatMessageReceive.getContent().isEmpty()){



            ActorAssetIssuer actorAssetIssuer   = new AssetIssuerActorRecord(null,fermatMessageReceive.getSender(),new byte[]{},0);


            Location loca = null;

            ActorAssetUser actorAssetUser  = new AssetUserActorRecord(fermatMessageReceive.getReceiver(), null, new byte[]{}, loca);

//            this.actorNetworkServiceAssetUser.handleRequestCryptoAddresFromRemoteAssetUserEvent(actorAssetIssuer,actorAssetUser);
            this.actorNetworkServiceAssetUser.handleRequestCryptoAddresFromRemoteAssetUserEvent(actorAssetUser);


        }else{

            CryptoAddress cryptoAddressRemote;

            Gson gson = new Gson();

            cryptoAddressRemote = gson.fromJson(fermatMessageReceive.getContent(),CryptoAddress.class);

            ActorAssetIssuer actorAssetIssuer   = new AssetIssuerActorRecord(null,fermatMessageReceive.getReceiver(),new byte[]{},0);


            Location loca = null;

            ActorAssetUser actorAssetUser  = new AssetUserActorRecord(fermatMessageReceive.getSender(), null, new byte[]{}, loca);

//            this.actorNetworkServiceAssetUser.handleDeliveredCryptoAddresFromRemoteAssetUserEvent(actorAssetUser,actorAssetIssuer,cryptoAddressRemote);
            this.actorNetworkServiceAssetUser.handleDeliveredCryptoAddresFromRemoteAssetUserEvent(actorAssetUser, cryptoAddressRemote);

        }


    }
}
