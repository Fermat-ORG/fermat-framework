package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.interfaces;

/**
 * Created by Angel on 2015.09.16..
 */

public interface CustomerBrokerBankMoneySale{

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    String getStatus();

    Float getAmount();

    Float getPriceReference();

    String getCurrencyPurchase();

    void setStatus(String status);

    void setAmount(Float amount);

    void setPriceReference(Float price);

    void setCurrencyPurchase(String currencyPurchase);
}
