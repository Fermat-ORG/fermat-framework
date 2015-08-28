package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.dmp_transaction.incoming_intra_user.IncomingIntraUserManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;

/**
 * Created by loui on 23/02/15.
 */
public class MoneyReceivedEventHandler implements EventHandler {
    IncomingIntraUserManager incomingIntraUserManager;

    public void setIncomingIntraUserManager(IncomingIntraUserManager incomingIntraUserManager){
        this.incomingIntraUserManager = incomingIntraUserManager;

    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {
        if (((Service) this.incomingIntraUserManager).getStatus() == ServiceStatus.STARTED){


        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
        
    }
}
