package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_purchase.interfaces;


import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_purchase.exceptions.CantCreateCustomerBrokerCryptoPurchaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerCryptoPurchaseManager {

    List<com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_purchase.interfaces.CustomerBrokerCryptoPurchase> getAllCustomerBrokerCryptoPurchaseFromCurrentDeviceUser() throws com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_purchase.exceptions.CantGetCustomerBrokerCryptoPurchaseException;

    com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_purchase.interfaces.CustomerBrokerCryptoPurchase createCustomerBrokerCryptoPurchase(
             final UUID contractId
            ,final String publicKeyBroker
            ,final String publicKeyCustomer
            ,final UUID paymentTransactionId
            ,final CurrencyType paymentCurrency
            ,final CurrencyType merchandiseCurrency
            ,final float merchandiseAmount
            ,final UUID executionTransactionId
            ,final CashCurrencyType cryptoCurrencyType
    ) throws CantCreateCustomerBrokerCryptoPurchaseException;

    void updateStatusCustomerBrokerCryptoPurchase(final UUID transactionId,final BusinessTransactionStatus transactionStatus) throws com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_purchase.exceptions.CantUpdateStatusCustomerBrokerCryptoPurchaseException;

}
