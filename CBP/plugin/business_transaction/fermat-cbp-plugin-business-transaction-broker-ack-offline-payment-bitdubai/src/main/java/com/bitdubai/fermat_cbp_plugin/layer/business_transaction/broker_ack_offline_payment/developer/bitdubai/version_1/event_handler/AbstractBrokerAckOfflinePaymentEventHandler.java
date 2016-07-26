package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/12/15.
 */
public abstract class AbstractBrokerAckOfflinePaymentEventHandler implements FermatEventHandler {

    public BrokerAckOfflinePaymentRecorderService brokerAckOfflinePaymentRecorderService;

    public void setBrokerAckOfflinePaymentRecorderService(
            BrokerAckOfflinePaymentRecorderService brokerAckOfflinePaymentRecorderService) throws
            CantSetObjectException {
        if (brokerAckOfflinePaymentRecorderService == null) {
            throw new CantSetObjectException("brokerAckOfflinePaymentRecorderService is null");
        }
        this.brokerAckOfflinePaymentRecorderService = brokerAckOfflinePaymentRecorderService;
    }

}
