package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraWalletUserActorPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantProcessNotificationsExceptions;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

/**
 * Created by natalia on 16/10/15.
 */
public class IntraWalletUserNewNotificationsEventHandlers implements FermatEventHandler {
    /**
     * listener ACTOR_NETWORK_SERVICE_NEW_NOTIFICATIONS event
     * Get Pendings Notifications from Network Services
     */
    private  IntraWalletUserActorPluginRoot intraUserActorPluginRoot = null;
    EventManager eventManager;
    FermatEventMonitor fermatEventMonitor;

    IntraUserManager intraUserNetworkServiceManager;

    public void setIntraWalletUserManager(IntraWalletUserActorPluginRoot intraUserActorPluginRoot){
        this.intraUserActorPluginRoot = intraUserActorPluginRoot;

    }

    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;

    }

    public void setEventManager(FermatEventMonitor fermatEventMonitor){
        this.fermatEventMonitor = fermatEventMonitor;

    }

    public void setIntraUserManager( IntraUserManager intraUserNetworkServiceManager){
        this.intraUserNetworkServiceManager = intraUserNetworkServiceManager;

    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        System.out.println("LLEGADA DE NOTIFICACION DE PENDIENTES A INTRA WALLET USER!!!");
        if (this.intraUserActorPluginRoot.getStatus() == ServiceStatus.STARTED){

            try {


                intraUserActorPluginRoot.processNotifications();
            }
            catch(CantProcessNotificationsExceptions e)
            {
                this.fermatEventMonitor.handleEventException(e, fermatEvent);
            }

            catch(Exception e)
            {
                this.fermatEventMonitor.handleEventException(e, fermatEvent);
            }

        }
        else {
            throw new TransactionServiceNotStartedException();
        }



    }
}
