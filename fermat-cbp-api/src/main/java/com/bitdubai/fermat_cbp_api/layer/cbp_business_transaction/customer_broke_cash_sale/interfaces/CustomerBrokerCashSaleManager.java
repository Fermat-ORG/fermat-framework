package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_sale.interfaces;


import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_sale.exceptions.CantCreateCustomerBrokerCashSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_sale.exceptions.CantGetCustomerBrokerCashSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_sale.exceptions.CantUpdateStatusCustomerBrokerCashSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_sale.interfaces.CustomerBrokerCashSale;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerCashSaleManager {

    List<CustomerBrokerCashSale> getAllCustomerBrokerCashSaleFromCurrentDeviceUser() throws CantGetCustomerBrokerCashSaleException;

    CustomerBrokerCashSale createCustomerBrokerCashSale(
             final String operationId
            ,final String contractId
            ,final String publicKeyCustomer
            ,final String publicKeyBroker
            ,final String merchandiseCurrency
            ,final float merchandiseAmount
            ,final String referenceCurrency
            ,final float referenceCurrencyPrice
            ,final String CashCurrencyType
    ) throws CantCreateCustomerBrokerCashSaleException;

    void updateStatusCustomerBrokerCashSale(final UUID transactionId) throws CantUpdateStatusCustomerBrokerCashSaleException;

}
