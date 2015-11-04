package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.contract.Contract;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;

import java.util.UUID;

/**
 * Created by Angel on 2015.09.16..
 */

public interface CustomerBrokerCashMoneyPurchase extends Contract{

    UUID getContractId();

    String getPublicKeyCustomer();

    UUID getPaymentTransactionId();

    CurrencyType getPaymentCurrency();

    CashCurrencyType getCashCurrencyType();

}
