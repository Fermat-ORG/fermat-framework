package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.exceptions.CantCreateCustomerBrokerCashMoneyPurchaseException;

import java.util.List;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerCashMoneyPurchaseManager {

    List<CustomerBrokerCashMoneyPurchase> getAllCustomerBrokerBankMoneyPurchaseFromCurrentDeviceUser();

    CustomerBrokerCashMoneyPurchase createCustomerBrokerBankMoneyPurchase(final String getPublicKeyCustomer, final String getPublicKeyBroker, final Float amount, final Float priceReference, final Float paymentCurrency) throws CantCreateCustomerBrokerCashMoneyPurchaseException;

}
