package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_sale.interfaces;


import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CryptoCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_sale.exceptions.CantGetCustomerBrokerCryptoSaleException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_sale.exceptions.CantUpdateStatusCustomerBrokerCryptoSaleException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerCryptoSaleManager {

    List<CustomerBrokerCryptoSale> getAllCustomerBrokerCryptoSaleFromCurrentDeviceUser() throws CantGetCustomerBrokerCryptoSaleException;

    CustomerBrokerCryptoSale createCustomerBrokerCryptoSale(
             final UUID contractId
            ,final String publicKeyBroker
            ,final String publicKeyCustomer
            ,final UUID paymentTransactionId
            ,final CurrencyType paymentCurrency
            ,final CurrencyType merchandiseCurrency
            ,final float merchandiseAmount
            ,final UUID executionTransactionId
            ,final CryptoCurrencyType cryptoCurrencyType
    ) throws com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_sale.exceptions.CantCreateCustomerBrokerCryptoSaleException;

    void updateStatusCustomerBrokerCryptoSale(final UUID transactionId,final BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCustomerBrokerCryptoSaleException;

}
