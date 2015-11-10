package com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.IncomingIntraUserTransactionPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantProcessMetaDataNotificationsExceptions;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;


/**
 * Created by loui on 23/02/15.
 */
public class IncomingCryptoMetadataEventHandler implements FermatEventHandler {
    /**
     * listener ACTOR_NETWORK_SERVICE_NEW_NOTIFICATIONS event
     * Get Pendings Notifications from Network Services
     */
    private IncomingIntraUserTransactionPluginRoot incomingIntraUserPluginRoot = null;
    EventManager eventManager;
    FermatEventMonitor fermatEventMonitor;

    IntraUserManager intraUserNetworkServiceManager;

    public void setIntraWalletUserManager(IncomingIntraUserTransactionPluginRoot incomingIntraUserPluginRoot){
        this.incomingIntraUserPluginRoot = incomingIntraUserPluginRoot;

    }

    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;

    }

    public void setEventManager(FermatEventMonitor fermatEventMonitor){
        this.fermatEventMonitor = fermatEventMonitor;

    }



        @Override
        public void handleEvent(FermatEvent fermatEvent) throws FermatException {

            System.out.println("LLEGADA DE METADATA PENDIENTES DE INCOMING!!!");
            if (((Service) this.incomingIntraUserPluginRoot).getStatus() == ServiceStatus.STARTED){

                try {


                    incomingIntraUserPluginRoot.processNotifications();
                }
                catch(CantProcessMetaDataNotificationsExceptions e)
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
