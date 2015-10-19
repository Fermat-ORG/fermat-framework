package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_purchase.interfaces;


import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_purchase.exceptions.CantCreateCustomerBrokerCryptoPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_purchase.exceptions.CantGetCustomerBrokerCryptoPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_purchase.exceptions.CantUpdateStatusCustomerBrokerCryptoPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_purchase.interfaces.CustomerBrokerCryptoPurchase;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerCryptoPurchaseManager {

    List<CustomerBrokerCryptoPurchase> getAllCustomerBrokerCryptoPurchaseFromCurrentDeviceUser() throws CantGetCustomerBrokerCryptoPurchaseException;

    CustomerBrokerCryptoPurchase createCustomerBrokerCryptoPurchase(
             final String contractId
            ,final String publicKeyCustomer
            ,final String paymentTransactionId
            ,final String paymentCurrency
            ,final String publicKeyBroker
            ,final String merchandiseCurrency
            ,final float merchandiseAmount
            ,final String executionTransactionId
            ,final String cryptoCurrencyType
    ) throws CantCreateCustomerBrokerCryptoPurchaseException;

    void updateStatusCustomerBrokerCryptoPurchase(final UUID transactionId) throws CantUpdateStatusCustomerBrokerCryptoPurchaseException;

}
