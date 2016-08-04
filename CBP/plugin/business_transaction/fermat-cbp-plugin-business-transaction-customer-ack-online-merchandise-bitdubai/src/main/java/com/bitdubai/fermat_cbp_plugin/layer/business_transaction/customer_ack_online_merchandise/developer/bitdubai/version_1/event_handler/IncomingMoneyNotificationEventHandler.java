package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/12/15.
 */
public class IncomingMoneyNotificationEventHandler extends AbstractCustomerAckOnlineMerchandiseEventHandler {
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.customerAckOnlineMerchandiseRecorderService.getStatus() == ServiceStatus.STARTED) {

            try {
                this.customerAckOnlineMerchandiseRecorderService.incomingMoneyNotification((IncomingMoneyNotificationEvent) fermatEvent);
            } catch (CantSaveEventException exception) {
                throw new CantSaveEventException(exception, "Handling the IncomingMoneyNotificationEventHandler", "Check the cause");
            } catch (ClassCastException exception) {
                //Logger LOG = Logger.getGlobal();
                //LOG.info("EXCEPTION DETECTOR----------------------------------");
                //exception.printStackTrace();
                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingMoneyNotificationEventHandler", "Cannot cast this event");
            } catch (Exception exception) {
                throw new CantSaveEventException(exception, "Handling the IncomingMoneyNotificationEventHandler", "Unexpected exception");
            }

        } else {
            throw new TransactionServiceNotStartedException();
        }
    }
}
