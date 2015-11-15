package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserEventRecorderService;

/**
 * Created by eze on 2015.06.19..
 */
public class IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventHandler implements FermatEventHandler {
    /**
     * IncomingCryptoIdentifiedEventHandler member variables
     */
    private final IncomingExtraUserEventRecorderService eventRecorderService;

    public IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventHandler(final IncomingExtraUserEventRecorderService eventRecorderService){
        this.eventRecorderService = eventRecorderService;
    }

    /**
     * FermatEventHandler interface implementation
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if(!eventRecorderService.getStatus().equals(ServiceStatus.STARTED))
            throw new TransactionServiceNotStartedException(TransactionServiceNotStartedException.DEFAULT_MESSAGE, null, null, "Events can't be handled if the service is not started");

        if(fermatEvent instanceof IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent)
            eventRecorderService.saveEvent(fermatEvent);
        else
            throw  new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantSaveEventException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantSaveEventException.DEFAULT_MESSAGE, null, "Event: " + fermatEvent.getEventType().toString(), "This should have been IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent");

    }

}