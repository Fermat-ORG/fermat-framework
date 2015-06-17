package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.dmp_transaction.incoming_intra_user.IncomingIntraUserManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;


/**
 * Created by loui on 23/02/15.
 */
public class IncomingCryptoIdentifiedFromIntraUserEventHandler implements EventHandler {
    IncomingIntraUserManager incomingIntraUserManager;
    
    public void setIncomingIntraUserManager(IncomingIntraUserManager incomingIntraUserManager){
        this.incomingIntraUserManager = incomingIntraUserManager;
        
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        if (((Service) this.incomingIntraUserManager).getStatus() == ServiceStatus.STARTED){

        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }
}


