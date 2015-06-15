package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_device_user.developer.bitdubai.version_1.Event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._18_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer._18_transaction.incoming_device_user.IncomingDeviceUserManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer.cry_3_crypto_module.actor_address_book.exceptions.ExampleException;

/**
 * Created by loui on 23/02/15.
 */
public class IncomingCryptoIdentifiedFromDeviceUserEventHandler implements EventHandler {
    
    IncomingDeviceUserManager incomingDeviceUserManager;
    
    public void setIncomingDeviceUserManager(IncomingDeviceUserManager incomingDeviceUserManager){
        this.incomingDeviceUserManager = incomingDeviceUserManager;
        
    }


    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        if (((Service) this.incomingDeviceUserManager).getStatus() == ServiceStatus.STARTED){

            try
            {
                this.incomingDeviceUserManager.exampleMethod();
            }
            catch (ExampleException exampleException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantCreateCryptoWalletException: "+ exampleException.getMessage());
                exampleException.printStackTrace();

                throw  exampleException;
            }
        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }
}
