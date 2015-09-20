package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.interfaces;


import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions.CantCreateCustomerBrokerBankMoneyPurchaseException;

import java.util.List;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerCashMoneyPurchaseManager {

    List<CustomerBrokerBankMoneyPurchase> getAllCustomerBrokerCashMoneyPurchaseFromCurrentDeviceUser();

    CustomerBrokerBankMoneyPurchase createCustomerBrokerCashMoneyPurchase(final String getPublicKeyCustomer, final String getPublicKeyBroker, final Float amount, final Float priceReference, final Float paymentCurrency) throws CantCreateCustomerBrokerBankMoneyPurchaseException;

}
