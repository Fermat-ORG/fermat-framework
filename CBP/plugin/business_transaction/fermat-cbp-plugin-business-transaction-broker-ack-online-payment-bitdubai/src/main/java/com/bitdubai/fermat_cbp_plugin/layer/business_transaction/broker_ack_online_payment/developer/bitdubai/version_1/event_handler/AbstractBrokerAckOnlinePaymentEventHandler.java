package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/12/15.
 */
public abstract class AbstractBrokerAckOnlinePaymentEventHandler implements FermatEventHandler {

    public BrokerAckOnlinePaymentRecorderService brokerAckOnlinePaymentRecorderService;

    public void setBrokerAckOnlinePaymentRecorderService(
            BrokerAckOnlinePaymentRecorderService brokerAckOnlinePaymentRecorderService) throws
            CantSetObjectException {
        if (brokerAckOnlinePaymentRecorderService == null) {
            throw new CantSetObjectException("brokerAckOnlinePaymentRecorderService is null");
        }
        this.brokerAckOnlinePaymentRecorderService = brokerAckOnlinePaymentRecorderService;
    }

}
