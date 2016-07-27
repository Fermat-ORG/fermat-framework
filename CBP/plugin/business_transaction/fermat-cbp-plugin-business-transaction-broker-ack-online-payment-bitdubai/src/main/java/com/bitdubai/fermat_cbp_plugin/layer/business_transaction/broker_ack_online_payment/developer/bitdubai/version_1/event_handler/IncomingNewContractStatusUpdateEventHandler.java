package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingNewContractStatusUpdate;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/12/15.
 */
public class IncomingNewContractStatusUpdateEventHandler extends AbstractBrokerAckOnlinePaymentEventHandler {
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.brokerAckOnlinePaymentRecorderService.getStatus() == ServiceStatus.STARTED) {

            try {
                this.brokerAckOnlinePaymentRecorderService.incomingNewContractStatusUpdateEventHandler((IncomingNewContractStatusUpdate) fermatEvent);
            } catch (CantSaveEventException exception) {
                throw new CantSaveEventException(exception, "Handling the incomingNewContractStatusUpdate", "Check the cause");
            } catch (ClassCastException exception) {
                //Logger LOG = Logger.getGlobal();
                //LOG.info("EXCEPTION DETECTOR----------------------------------");
                //exception.printStackTrace();
                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the incomingNewContractStatusUpdate", "Cannot cast this event");
            } catch (Exception exception) {
                throw new CantSaveEventException(exception, "Handling the incomingNewContractStatusUpdate", "Unexpected exception");
            }

        } else {
            throw new TransactionServiceNotStartedException();
        }
    }
}
