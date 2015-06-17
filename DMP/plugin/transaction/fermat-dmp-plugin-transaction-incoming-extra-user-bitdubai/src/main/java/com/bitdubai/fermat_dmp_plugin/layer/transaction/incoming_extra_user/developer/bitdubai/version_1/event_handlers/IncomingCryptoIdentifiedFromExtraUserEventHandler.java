package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.dmp_transaction.incoming_extra_user.IncomingExtraUserManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;


/**
 * Created by loui on 23/02/15.
 */
public class IncomingCryptoIdentifiedFromExtraUserEventHandler implements EventHandler {

    IncomingExtraUserManager incomingExtraUserManager;

    public void setIncomingExtraUserManager(IncomingExtraUserManager incomingExtraUserManager){
        this.incomingExtraUserManager = incomingExtraUserManager;

    }

    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        if (((Service) this.incomingExtraUserManager).getStatus() == ServiceStatus.STARTED){


        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }
}
