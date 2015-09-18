package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.interfaces;

/**
 * Created by Angel on 2015.09.16..
 */

public interface CustomerBrokerBankMoneyPurchase{

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    String getStatus();

    Float getAmount();

    Float getPriceReference();

    String getPaymentCurrency();

    void setStatus(String status);

    void setAmount(Float amount);

    void setPriceReference(Float price);

    void setPaymentCurrency(String paymentCurrency);
}
