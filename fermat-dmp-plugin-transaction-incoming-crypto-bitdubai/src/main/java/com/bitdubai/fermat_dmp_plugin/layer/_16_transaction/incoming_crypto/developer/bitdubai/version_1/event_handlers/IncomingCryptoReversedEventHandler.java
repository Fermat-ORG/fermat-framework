package com.bitdubai.fermat_dmp_plugin.layer._16_transaction.incoming_crypto.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.layer._18_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingCryptoReversedEvent;
import com.bitdubai.fermat_dmp_plugin.layer._16_transaction.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSaveEvent;
import com.bitdubai.fermat_dmp_plugin.layer._16_transaction.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoEventRecorderService;

/**
 * Created by loui on 22/02/15.
 * Modified by Arturo Vallone 25/04/2015
 */
public class IncomingCryptoReversedEventHandler implements EventHandler {
    /**
     * IncomingCryptoReversedEventHandler member variables
     */
    IncomingCryptoEventRecorderService incomingCryptoEventRecorderService;


    /**
     * IncomingCryptoReversedEventHandler member methods
     */
    public void setIncomingCryptoEventRecorderService(IncomingCryptoEventRecorderService incomingCryptoEventRecorderService){
        this.incomingCryptoEventRecorderService = incomingCryptoEventRecorderService;
    }

    /**
     * EventHandler interface implementation
     */
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {

        if (this.incomingCryptoEventRecorderService.getStatus() == ServiceStatus.STARTED){

            try
            {
                this.incomingCryptoEventRecorderService.incomingCryptoReversed((IncomingCryptoReversedEvent) platformEvent);
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
            catch (ClassCastException classCastException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("ClassCastException: "+ classCastException.getMessage());
                classCastException.printStackTrace();

                throw  new CantSaveEvent();
            }
        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }

}