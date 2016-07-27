package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransactionEvent;

/**
 * Created by Yordin Alayn 10.12.15
 */
public class IncomingNegotiationTransactionEventHandler extends AbstractCustomerBrokerNewEventHandler {
//public class IncomingNegotiationTransactionEventHandler implements FermatEventHandler {

    public IncomingNegotiationTransactionEventHandler() {
//        System.out.print("\n\n**** 6.0) MOCK CUSTOMER BROKER NEW RECEIVE, EVENT HANDLER - LISTENER EVENT ****\n");
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
//        System.out.print("\n\n**** 6.1) MOCK CUSTOMER BROKER NEW RECEIVE, EVENT HANDLER - LISTENER EVENT ****\n");
        if (this.customerBrokerNewServiceEventHandler.getStatus() == ServiceStatus.STARTED) {

            try {

//                System.out.print("\n\n**** 6.2) MOCK CUSTOMER BROKER NEW RECEIVE, EVENT HANDLER - LISTENER EVENT ****\n");

                this.customerBrokerNewServiceEventHandler.incomingNegotiationTransactionEventHandler((IncomingNegotiationTransactionEvent) fermatEvent);

            } catch (CantSaveEventException exception) {
                throw new CantSaveEventException(exception, "Handling the IncomingNegotiationTransactionEventHandler", "Check the cause");
            } catch (ClassCastException exception) {
                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingNegotiationTransactionEventHandler", "Cannot cast this event");
            } catch (Exception exception) {
                throw new CantSaveEventException(exception, "Handling the IncomingNegotiationTransactionEventHandler", "Unexpected exception");
            }

        } else {
            throw new TransactionServiceNotStartedException();
        }
    }
}
