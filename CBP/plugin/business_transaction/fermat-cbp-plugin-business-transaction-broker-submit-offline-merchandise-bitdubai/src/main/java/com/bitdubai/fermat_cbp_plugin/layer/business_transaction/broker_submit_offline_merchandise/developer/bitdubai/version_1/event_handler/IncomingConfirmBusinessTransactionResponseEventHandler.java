package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingConfirmBusinessTransactionResponse;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/12/15.
 */
public class IncomingConfirmBusinessTransactionResponseEventHandler extends AbstractBrokerSubmitOfflineMerchandiseEventHandler {
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.brokerSubmitOfflineMerchandiseRecorderService.getStatus() == ServiceStatus.STARTED) {

            try {
                this.brokerSubmitOfflineMerchandiseRecorderService.incomingConfirmBusinessTransactionResponse((IncomingConfirmBusinessTransactionResponse) fermatEvent);
            } catch (CantSaveEventException exception) {
                throw new CantSaveEventException(exception, "Handling the IncomingConfirmBusinessTransactionResponse", "Check the cause");
            } catch (ClassCastException exception) {
                //Logger LOG = Logger.getGlobal();
                //LOG.info("EXCEPTION DETECTOR----------------------------------");
                //exception.printStackTrace();
                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingConfirmBusinessTransactionResponse", "Cannot cast this event");
            } catch (Exception exception) {
                throw new CantSaveEventException(exception, "Handling the IncomingConfirmBusinessTransactionResponse", "Unexpected exception");
            }

        } else {
            throw new TransactionServiceNotStartedException();
        }
    }
}
