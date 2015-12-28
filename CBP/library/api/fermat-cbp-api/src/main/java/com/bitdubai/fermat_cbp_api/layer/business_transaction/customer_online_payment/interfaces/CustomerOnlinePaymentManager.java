package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CustomerPaymentManager;

/**
 * This interface extends from
 * <code>com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CustomerPaymentManager</code>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public interface CustomerOnlinePaymentManager extends CustomerPaymentManager{

    /**
     * This method send a payment according the contract elements.
     * @param contractHash
     * @throws CantSendPaymentException
     */
    void sendPayment(String walletPublicKey, String contractHash)throws CantSendPaymentException;

}
