package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_sale.interfaces;


import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_sale.exceptions.CantCreateCustomerBrokerCashSaleException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_sale.exceptions.CantGetCustomerBrokerCashSaleException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_sale.exceptions.CantUpdateStatusCustomerBrokerCashSaleException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerCashSaleManager {

    List<CustomerBrokerCashSale> getAllCustomerBrokerCashSaleFromCurrentDeviceUser() throws CantGetCustomerBrokerCashSaleException;

    CustomerBrokerCashSale createCustomerBrokerCashSale(
             final UUID contractId
            ,final String publicKeyBroker
            ,final String publicKeyCustomer
            ,final UUID paymentTransactionId
            ,final CurrencyType paymentCurrency
            ,final CurrencyType merchandiseCurrency
            ,final float merchandiseAmount
            ,final UUID executionTransactionId
            ,final CashCurrencyType cashCurrencyType
    ) throws CantCreateCustomerBrokerCashSaleException;

    void updateStatusCustomerBrokerCashSale(final UUID transactionId,final BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCustomerBrokerCashSaleException;

}
