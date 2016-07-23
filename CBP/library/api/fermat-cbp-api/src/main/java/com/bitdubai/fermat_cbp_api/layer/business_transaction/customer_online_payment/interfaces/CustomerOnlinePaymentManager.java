package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CustomerPaymentManager;


/**
 * This interface extends from
 * <code>com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CustomerPaymentManager</code>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public interface CustomerOnlinePaymentManager extends CustomerPaymentManager {

    /**
     * This method send a payment according the contract elements.
     *
     * @param walletPublicKey the customer wallet public key
     * @param contractHash    the contract Hash/ID
     * @param paymentCurrency the payment crypto currency
     * @throws CantSendPaymentException
     */
    void sendPayment(
            String walletPublicKey,
            String contractHash,
            CryptoCurrency paymentCurrency,
            FeeOrigin feeOrigin,
            long fee) throws CantSendPaymentException;

    /**
     * This method send a payment according the contract elements.
     *
     * @param walletPublicKey       the customer wallet public key
     * @param contractHash          the contract Hash/ID
     * @param paymentCurrency       the payment crypto currency
     * @param blockchainNetworkType the Blockchain Network Type
     * @throws CantSendPaymentException
     */
    void sendPayment(
            String walletPublicKey,
            String contractHash,
            CryptoCurrency paymentCurrency,
            BlockchainNetworkType blockchainNetworkType,
            FeeOrigin feeOrigin,
            long fee)
            throws CantSendPaymentException;

}
