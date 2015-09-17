package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoTransactionsWaitingTransferenceEvent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSaveEvent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoEventRecorderService;

/**
 * Created by eze on 2015.06.19..
 */
public class IncomingCryptoTransactionsWaitingTransferenceEventHandler implements EventHandler {
    /**
     * IncomingCryptoIdentifiedEventHandler member variables
     */
    IncomingCryptoEventRecorderService incomingCryptoEventRecorderService;


    /**
     * IncomingCryptoIdentifiedEventHandler member methods
     */
    public void setIncomingCryptoEventRecorderService(IncomingCryptoEventRecorderService incomingCryptoEventRecorderService){
        this.incomingCryptoEventRecorderService = incomingCryptoEventRecorderService;
    }


    /**
     * EventHandler interface implementation
     */
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException{
        /**
         * Modified by Franklin Marcano, 03/08/2015
         */
        if (this.incomingCryptoEventRecorderService.getStatus() == ServiceStatus.STARTED){

            try
            {
                this.incomingCryptoEventRecorderService.incomingCryptoWaitingTransference((IncomingCryptoTransactionsWaitingTransferenceEvent) platformEvent);
            }
            catch (CantSaveEvent cantSaveEvent)
            {
                throw  cantSaveEvent;
            }
            catch (ClassCastException classCastException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                throw  new CantSaveEvent("classCastException found", FermatException.wrapException(classCastException),"","");
            }
            catch (Exception e)
            {
                throw  new CantSaveEvent("Exception unchecked", FermatException.wrapException(e),"","");
            }
        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }

}