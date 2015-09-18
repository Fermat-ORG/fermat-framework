package com.bitdubai.fermat_csh_api.all_definition.cash_money_transaction;

/**
 * Created by Yordin Alayn on 18.09.2015
 */

public interface CashMoneyTransaction {

    String getTransactionId();

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    String getStatus();

    Float getAmount();

}