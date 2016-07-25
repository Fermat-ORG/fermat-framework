package com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_offline_payment.interfaces;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BrokerAckPaymentManager;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/12/15.
 */
public interface BrokerAckOfflinePaymentManager extends BrokerAckPaymentManager {

    /**
     * This method send a payment according the contract elements.
     *
     * @param walletPublicKey the broker wallet public key
     * @param contractHash    the contract hash
     * @param actorPublicKey  the actor public key
     * @param customerAlias   the customer alias
     * @throws CantAckPaymentException
     */
    void ackPayment(String walletPublicKey, String contractHash, String actorPublicKey, String customerAlias) throws CantAckPaymentException;
}
