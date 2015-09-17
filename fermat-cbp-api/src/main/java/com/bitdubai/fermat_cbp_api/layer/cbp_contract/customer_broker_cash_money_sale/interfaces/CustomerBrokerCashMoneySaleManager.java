package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_sale.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_sale.exceptions.CantCreateCustomerBrokerCashMoneySaleException;

import java.util.List;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerCashMoneySaleManager {

    List<CustomerBrokerCashMoneySale> getAllCustomerBrokerCashMoneySaleFromCurrentDeviceUser();

    CustomerBrokerCashMoneySale createCustomerBrokerCashMoneySale(final String getPublicKeyBroker, final String getPublicKeyCustomer, final Float amount, final Float priceReference, final Float currencyPurchase) throws CantCreateCustomerBrokerCashMoneySaleException;

}
