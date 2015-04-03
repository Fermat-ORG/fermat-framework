package com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.layer._13_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSaveEvent;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionService;
import com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoEventRecorderService;

/**
 * Created by loui on 22/02/15.
 */
public class IncomingCryptoReceptionConfirmedEventHandler implements EventHandler {
    IncomingCryptoEventRecorderService incomingCryptoEventRecorderService;

    public void setIncomingCryptoEventRecorder(IncomingCryptoEventRecorderService incomingCryptoEventRecorderService){
        this.incomingCryptoEventRecorderService = incomingCryptoEventRecorderService;
    }

    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {

        if (((TransactionService) this.incomingCryptoEventRecorderService).getStatus() == ServiceStatus.STARTED){

            try
            {
                this.incomingCryptoEventRecorderService.incomingCryptoReceptionConfirmed();
            }
            catch (CantSaveEvent cantSaveEvent)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantSaveEvent: "+ cantSaveEvent.getMessage());
                cantSaveEvent.printStackTrace();

                throw  cantSaveEvent;
            }
        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }
}