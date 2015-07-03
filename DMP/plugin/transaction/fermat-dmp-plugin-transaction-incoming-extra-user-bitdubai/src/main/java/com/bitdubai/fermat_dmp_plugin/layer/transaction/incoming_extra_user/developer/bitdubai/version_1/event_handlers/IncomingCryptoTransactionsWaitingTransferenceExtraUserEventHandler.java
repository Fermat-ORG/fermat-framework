package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserEventRecorderService;

/**
 * Created by eze on 2015.06.19..
 */
public class IncomingCryptoTransactionsWaitingTransferenceExtraUserEventHandler implements EventHandler {
    /**
     * IncomingCryptoIdentifiedEventHandler member variables
     */
    IncomingExtraUserEventRecorderService incomingExtraUserEventRecorderService;


    /**
     * IncomingCryptoIdentifiedEventHandler member methods
     */
    public void setIncomingExtraUserEventRecorderService(IncomingExtraUserEventRecorderService incomingExtraUserEventRecorderService){
        this.incomingExtraUserEventRecorderService = incomingExtraUserEventRecorderService;
    }


    /**
     * EventHandler interface implementation
     */
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {

        if (this.incomingExtraUserEventRecorderService.getStatus() == ServiceStatus.STARTED){

            try
            {
                this.incomingExtraUserEventRecorderService.incomingCryptoWaitingTransference((IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent) platformEvent);
            }
            catch (CantSaveEventException cantSaveEvent)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantSaveEventException: "+ cantSaveEvent.getMessage());
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

                throw  new CantSaveEventException();
            }
        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }

}