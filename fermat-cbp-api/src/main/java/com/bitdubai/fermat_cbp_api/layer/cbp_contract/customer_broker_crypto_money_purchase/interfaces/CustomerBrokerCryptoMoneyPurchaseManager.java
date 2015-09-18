package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.exceptions.CantCreateCustomerBrokerCryptoMoneyPurchaseException;

import java.util.List;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerCryptoMoneyPurchaseManager {

    List<CustomerBrokerCryptoMoneyPurchase> getAllCustomerBrokerCryptoMoneyPurchaseFromCurrentDeviceUser();

    CustomerBrokerCryptoMoneyPurchase createCustomerBrokerCryptoMoneyPurchase(final String getPublicKeyCustomer, final String getPublicKeyBroker, final Float amount, final Float priceReference, final Float paymentCurrency) throws CantCreateCustomerBrokerCryptoMoneyPurchaseException;

}
