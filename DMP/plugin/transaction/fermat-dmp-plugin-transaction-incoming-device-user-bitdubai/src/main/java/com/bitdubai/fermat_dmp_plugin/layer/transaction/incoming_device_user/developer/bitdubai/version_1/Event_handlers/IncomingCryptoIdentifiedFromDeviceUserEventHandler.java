package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_device_user.developer.bitdubai.version_1.Event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.dmp_transaction.incoming_device_user.IncomingDeviceUserManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;


/**
 * Created by loui on 23/02/15.
 */
public class IncomingCryptoIdentifiedFromDeviceUserEventHandler implements FermatEventHandler {
    
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
