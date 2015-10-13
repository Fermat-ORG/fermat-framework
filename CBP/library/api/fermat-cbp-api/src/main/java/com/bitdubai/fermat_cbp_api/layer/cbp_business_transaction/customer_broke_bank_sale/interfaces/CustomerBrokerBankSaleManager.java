package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.interfaces;


import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.exceptions.CantCreateCustomerBrokerBankSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.exceptions.CantGetCustomerBrokerBankSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.exceptions.CantUpdateStatusCustomerBrokerBankSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.interfaces.CustomerBrokerBankSale;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerBankSaleManager {

    List<CustomerBrokerBankSale> getAllCustomerBrokerBankSaleFromCurrentDeviceUser() throws CantGetCustomerBrokerBankSaleException;

    CustomerBrokerBankSale createCustomerBrokerBankSale(
             final String contractId
            ,final String publicKeyCustomer
            ,final String paymentTransactionId
            ,final String paymentCurrency
            ,final String publicKeyBroker
            ,final String merchandiseCurrency
            ,final float merchandiseAmount
            ,final String executionTransactionId
            ,final String bankCurrencyType
            ,final String bankOperationType
    ) throws CantCreateCustomerBrokerBankSaleException;

    void updateStatusCustomerBrokerBankSale(final UUID transactionId) throws CantUpdateStatusCustomerBrokerBankSaleException;

}
