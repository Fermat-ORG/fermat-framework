package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_sale.interfaces;


import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_sale.exceptions.CantCreateCustomerBrokerCryptoSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_sale.exceptions.CantGetCustomerBrokerCryptoSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_sale.exceptions.CantUpdateStatusCustomerBrokerCryptoSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_sale.interfaces.CustomerBrokerCryptoSale;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerCryptoSaleManager {

    List<CustomerBrokerCryptoSale> getAllCustomerBrokerCryptoSaleFromCurrentDeviceUser() throws CantGetCustomerBrokerCryptoSaleException;

    CustomerBrokerCryptoSale createCustomerBrokerCryptoSale(
             final String contractId
            ,final String publicKeyCustomer
            ,final String paymentTransactionId
            ,final String paymentCurrency
            ,final String publicKeyBroker
            ,final String merchandiseCurrency
            ,final float merchandiseAmount
            ,final String executionTransactionId
            ,final String cryptoCurrencyType
    ) throws CantCreateCustomerBrokerCryptoSaleException;

    void updateStatusCustomerBrokerCryptoSale(final UUID transactionId) throws CantUpdateStatusCustomerBrokerCryptoSaleException;

}
