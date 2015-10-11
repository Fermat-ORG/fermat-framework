package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAcceptAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.AssetUserActorConnectionAcceptedEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

/**
 * Created by Nerio on 08/10/15.
 */
public class AssetUserActorConnectionAcceptedEventHandlers implements FermatEventHandler {

    /**
     * listener  ASSET_USER_CONNECTION_ACCEPTED event
     * Change Actor status to ACCEPTED
     */
    ActorAssetUserManager actorAssetUserManager;
    EventManager eventManager;
    FermatEventMonitor fermatEventMonitor;

    AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager;

    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) {
        this.actorAssetUserManager = actorAssetUserManager;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void setEventManager(FermatEventMonitor fermatEventMonitor) {
        this.fermatEventMonitor = fermatEventMonitor;
    }

    public void setAssetUserActorNetworkServiceManager(AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager) {
        this.assetUserActorNetworkServiceManager = assetUserActorNetworkServiceManager;
    }

    /**
     * Throw the method <code>handleEvent</code> you can handle the fermat event.
     *
     * @param fermatEvent event to be handled.
     * @throws FermatException if something goes wrong.
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (((Service) this.actorAssetUserManager).getStatus() == ServiceStatus.STARTED) {

            try {
                AssetUserActorConnectionAcceptedEvent assetUserActorConnectionAcceptedEvent = (AssetUserActorConnectionAcceptedEvent) fermatEvent;
                /**
                 * Change ASSET USER ACTOR Status To Connected
                 */
                this.actorAssetUserManager.acceptAssetUserActor(assetUserActorConnectionAcceptedEvent.getAssetUserLinkedIdentiyPublicKey(),
                        assetUserActorConnectionAcceptedEvent.getAssetUserToAddPublicKey());
                /**
                 * Confirm connexion on Network services
                 */
                //TODO DEBE EDITARSE parametro PARA PODER HACER EL registerActorAssetUser COMPLETO
//                assetUserActorNetworkServiceManager.registerActorAssetUser(assetUserActorConnectionAcceptedEvent.getAssetUserToAddPublicKey());

//                 assetUserActorNetworkServiceManager.requestListActorAssetUserRegistered();
//                /**
//                 * fire event "INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION"
//                 */
//                FermatEvent event = eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION);
//                eventManager.raiseEvent(event);

            } catch (CantAcceptAssetUserActorException e) {
                this.fermatEventMonitor.handleEventException(e, fermatEvent);
            } catch (Exception e) {
                this.fermatEventMonitor.handleEventException(e, fermatEvent);
            }
        } else {
            throw new TransactionServiceNotStartedException();
        }
    }
}
