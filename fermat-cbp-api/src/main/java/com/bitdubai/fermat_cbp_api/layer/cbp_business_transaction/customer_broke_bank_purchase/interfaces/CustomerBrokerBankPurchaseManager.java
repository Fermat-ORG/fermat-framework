package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.interfaces;


import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.exceptions.CantCreateCustomerBrokerBankPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.exceptions.CantGetCustomerBrokeBankPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.exceptions.CantUpdateStatusCustomerBrokeBankPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.interfaces.CustomerBrokerBankPurchase;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public interface CustomerBrokerBankPurchaseManager {

    List<CustomerBrokerBankPurchase> getAllCustomerBrokerBankPurchaseFromCurrentDeviceUser() throws CantGetCustomerBrokeBankPurchaseException;

    CustomerBrokerBankPurchase createCustomerBrokerBankPurchase(
             final String operationId
            ,final String contractId
            ,final String publicKeyCustomer
            ,final String publicKeyBroker
            ,final String merchandiseCurrency
            ,final float merchandiseAmount
            ,final String referenceCurrency
            ,final float referenceCurrencyPrice
            ,final String cryptoCurrency
            ,final String cryptoAddress
            ,final String bankName
            ,final String bankAccountNumber
            ,final String bankAccountType
            ,final String bankDocumentReference
    ) throws CantCreateCustomerBrokerBankPurchaseException;

    void updateStatusCustomerBrokerBankPurchase(final UUID transactionId) throws CantUpdateStatusCustomerBrokeBankPurchaseException;

}
