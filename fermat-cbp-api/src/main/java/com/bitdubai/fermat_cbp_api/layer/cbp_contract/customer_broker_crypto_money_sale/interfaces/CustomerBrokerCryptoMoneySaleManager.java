package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_sale.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_sale.exceptions.CantCreateCustomerBrokerCryptoMoneySaleException;

import java.util.List;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerCryptoMoneySaleManager {

    List<CustomerBrokerCryptoMoneySale> getAllCustomerBrokerCryptoMoneySaleFromCurrentDeviceUser();

    CustomerBrokerCryptoMoneySale createCustomerBrokerCryptoMoneySale(final String getPublicKeyBroker, final String getPublicKeyCustomer, final Float amount, final Float priceReference, final Float currencyPurchase) throws CantCreateCustomerBrokerCryptoMoneySaleException;

}
