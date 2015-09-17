package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.interfaces.CustomerBrokerBankMoneyPurchase;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.exceptions.CantCreateCustomerBrokerBankMoneySaleException;

import java.util.List;

/**
 * Created by angel on 16/9/15.
 */
public interface CustomerBrokerBankMoneySaleManager {

    List<CustomerBrokerBankMoneyPurchase> getAllCustomerBrokerBankMoneySaleFromCurrentDeviceUser();

    CustomerBrokerBankMoneyPurchase createCustomerBrokerBankMoneySale(final String getPublicKeyBroker, final String getPublicKeyCustomer, final Float amount, final Float priceReference, final Float currencyPurchase) throws CantCreateCustomerBrokerBankMoneySaleException;

}
