package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;


/**
 * Created by loui on 23/02/15.
 */
public class IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEventHandler implements FermatEventHandler {
    com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserEventRecorderService incomingIntraUserEventRecorderService;

    public IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEventHandler(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserEventRecorderService incomingIntraUserEventRecorderService){
        this.incomingIntraUserEventRecorderService = incomingIntraUserEventRecorderService;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.incomingIntraUserEventRecorderService.getServiceStatus().equals(ServiceStatus.STARTED))
            this.incomingIntraUserEventRecorderService.saveEvent(fermatEvent);
        else
            throw new TransactionServiceNotStartedException();
    }
}
