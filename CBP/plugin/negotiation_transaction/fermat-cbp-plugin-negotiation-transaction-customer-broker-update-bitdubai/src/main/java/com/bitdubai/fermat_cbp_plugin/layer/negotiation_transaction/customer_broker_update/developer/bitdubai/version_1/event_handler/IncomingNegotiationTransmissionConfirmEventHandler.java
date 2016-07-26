package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransmissionConfirmNegotiationEvent;

/**
 * Created by Yordin Alayn 18.12.15
 */
public class IncomingNegotiationTransmissionConfirmEventHandler extends AbstractCustomerBrokerUpdateEventHandler {

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.customerBrokerUpdateServiceEventHandler.getStatus() == ServiceStatus.STARTED) {

            try {
                this.customerBrokerUpdateServiceEventHandler.incomingNegotiationTransactionConfirmEventHandler((IncomingNegotiationTransmissionConfirmNegotiationEvent) fermatEvent);
            } catch (CantSaveEventException exception) {
                throw new CantSaveEventException(exception, "Handling the IncomingNegotiationTransmissionConfirmEventHandler", "Check the cause");
            } catch (ClassCastException exception) {
                //Logger LOG = Logger.getGlobal();
                //LOG.info("EXCEPTION DETECTOR----------------------------------");
                //exception.printStackTrace();
                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingNegotiationTransmissionConfirmEventHandler", "Cannot cast this event");
            } catch (Exception exception) {
                throw new CantSaveEventException(exception, "Handling the IncomingNegotiationTransmissionConfirmEventHandler", "Unexpected exception");
            }

        } else {
            throw new TransactionServiceNotStartedException();
        }
    }
}
