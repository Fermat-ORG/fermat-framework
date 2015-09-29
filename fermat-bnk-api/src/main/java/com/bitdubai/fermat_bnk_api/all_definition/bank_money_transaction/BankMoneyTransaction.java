package com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankMoneyTransactionType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 18.09.2015
 */

public interface BankMoneyTransaction {

    UUID getBankTransactionId();

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    BankTransactionStatus getStatus();

    BankMoneyTransactionType getBankMoneyTransactionType();

    float getMerchandiseAmount();

    BankCurrencyType getBankCurrencyType();

    BankOperationType getBankOperationType();

    String getBankName();

    String getBankAccountNumber();

    BankAccountType getBankAccountType();

    String getBankDocumentReference();

}