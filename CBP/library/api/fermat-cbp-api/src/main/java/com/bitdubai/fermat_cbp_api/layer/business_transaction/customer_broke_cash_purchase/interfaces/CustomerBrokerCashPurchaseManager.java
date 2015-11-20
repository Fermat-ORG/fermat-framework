package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_purchase.interfaces;


import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_purchase.exceptions.CantGetCustomerBrokerCashPurchaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerCashPurchaseManager {

    List<CustomerBrokerCashPurchase> getAllCustomerBrokerCashPurchaseFromCurrentDeviceUser() throws CantGetCustomerBrokerCashPurchaseException;

    CustomerBrokerCashPurchase createCustomerBrokerCashPurchase(
             final UUID contractId
            ,final String publicKeyBroker
            ,final String publicKeyCustomer
            ,final UUID paymentTransactionId
            ,final CurrencyType paymentCurrency
            ,final CurrencyType merchandiseCurrency
            ,final float merchandiseAmount
            ,final UUID executionTransactionId
            ,final CashCurrencyType cashCurrencyType
    ) throws com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_purchase.exceptions.CantCreateCustomerBrokerCashPurchaseException;

    void updateStatusCustomerBrokerCashPurchase(final UUID transactionId,final BusinessTransactionStatus transactionStatus) throws com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_purchase.exceptions.CantUpdateStatusCustomerBrokerCashPurchaseException;

}
