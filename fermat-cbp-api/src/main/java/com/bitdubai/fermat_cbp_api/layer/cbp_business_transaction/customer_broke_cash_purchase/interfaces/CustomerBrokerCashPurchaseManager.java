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
             final String operationId
            ,final String contractId
            ,final String publicKeyCustomer
            ,final String publicKeyBroker
            ,final String merchandiseCurrency
            ,final float merchandiseAmount
            ,final String referenceCurrency
            ,final float referenceCurrencyPrice
            ,final String CashCurrencyType
    ) throws CantCreateCustomerBrokerCashPurchaseException;

    void updateStatusCustomerBrokerCashPurchase(final UUID transactionId) throws CantUpdateStatusCustomerBrokerCashPurchaseException;

}
