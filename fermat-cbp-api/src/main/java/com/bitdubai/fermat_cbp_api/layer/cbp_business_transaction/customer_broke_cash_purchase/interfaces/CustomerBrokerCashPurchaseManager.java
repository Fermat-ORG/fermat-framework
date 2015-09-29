package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_purchase.interfaces;


import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_purchase.exceptions.CantCreateCustomerBrokerCashPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_purchase.exceptions.CantGetCustomerBrokerCashPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_purchase.exceptions.CantUpdateStatusCustomerBrokerCashPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_purchase.interfaces.CustomerBrokerCashPurchase;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerCashPurchaseManager {

    List<CustomerBrokerCashPurchase> getAllCustomerBrokerCashPurchaseFromCurrentDeviceUser() throws CantGetCustomerBrokerCashPurchaseException;

    CustomerBrokerCashPurchase createCustomerBrokerCashPurchase(
             final String contractId
            ,final String publicKeyCustomer
            ,final String paymentTransactionId
            ,final String paymentCurrency
            ,final String publicKeyBroker
            ,final String merchandiseCurrency
            ,final String executionTransactionId
            ,final String cashCurrencyType
    ) throws CantCreateCustomerBrokerCashPurchaseException;

    void updateStatusCustomerBrokerCashPurchase(final UUID transactionId) throws CantUpdateStatusCustomerBrokerCashPurchaseException;

}
