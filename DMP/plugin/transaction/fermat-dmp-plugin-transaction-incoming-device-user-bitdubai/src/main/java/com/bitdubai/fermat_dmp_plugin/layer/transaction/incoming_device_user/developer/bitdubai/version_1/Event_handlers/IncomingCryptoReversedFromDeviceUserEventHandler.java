package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_device_user.developer.bitdubai.version_1.Event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.dmp_transaction.incoming_device_user.IncomingDeviceUserManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEventHandler;


/**
 * Created by loui on 23/02/15.
 */
public class IncomingCryptoReversedFromDeviceUserEventHandler implements FermatEventHandler {
    
    IncomingDeviceUserManager incomingDeviceUserManager;
    
    public void setIncomingDeviceUserManager(IncomingDeviceUserManager incomingDeviceUserManager){
        this.incomingDeviceUserManager = incomingDeviceUserManager;
        
    }


    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (((Service) this.incomingDeviceUserManager).getStatus() == ServiceStatus.STARTED){


        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }
}
