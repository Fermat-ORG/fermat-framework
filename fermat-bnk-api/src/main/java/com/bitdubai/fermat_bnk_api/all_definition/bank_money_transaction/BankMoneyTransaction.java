package com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction;

/**
 * Created by Yordin Alayn on 18.09.2015
 */

public interface BankMoneyTransaction {

    String getTransactionId();

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    String getStatus();

    Float getAmount();

    String getBank();

    String getBankReference();

}