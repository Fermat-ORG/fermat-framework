//package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers;
//
//import com.bitdubai.fermat_api.FermatException;
//import com.bitdubai.fermat_api.Service;
//import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
//import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
//import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
//import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorNetworkServiceAssetUser;
//import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.ActorAssetUserRequestCryptoAddressNotificationEvent;
//
///**
// * Created by Nerio on 27/10/15.
// */
//public class ActorAssetUserRequestCryptoAddressNotificationEventHandler implements FermatEventHandler {
//
//    private ActorNetworkServiceAssetUser actorNetworkServiceAssetUser;
//
//    public ActorAssetUserRequestCryptoAddressNotificationEventHandler(ActorNetworkServiceAssetUser actorNetworkServiceAssetUser) {
//        this.actorNetworkServiceAssetUser = actorNetworkServiceAssetUser;
//    }
//
//    @Override
//    public void handleEvent(FermatEvent platformEvent) throws FermatException {
//
//        System.out.println("Request Crypto Address Asset User Actor - handleEvent =" + platformEvent);
//
//        if (((Service) this.actorNetworkServiceAssetUser).getStatus() == ServiceStatus.STARTED) {
//
//            ActorAssetUserRequestCryptoAddressNotificationEvent actorAssetUserRequestCryptoAddressNotificationEvent = (ActorAssetUserRequestCryptoAddressNotificationEvent) platformEvent;
//             /*
//             *  ActorNetworkServiceAssetUser make the job
//             */
////            this.actorNetworkServiceAssetUser.handleCompleteRequestCryptoAddressNotificationEvent(actorAssetUserRequestCryptoAddressNotificationEvent.getActorAssetUser());
//        }
//    }
//}
