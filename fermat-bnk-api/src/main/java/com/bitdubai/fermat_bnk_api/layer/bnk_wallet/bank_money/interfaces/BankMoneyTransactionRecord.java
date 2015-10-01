package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;

/**
 * Created by Yordin Alayn on 30.09.15.
 */
public interface BankMoneyTransactionRecord {

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    BankTransactionStatus getStatus();

    float getMerchandiseAmount();

    BankCurrencyType getBankCurrencyType();

    BankOperationType getBankOperationType();

    String getBankName();

    String getBankAccountNumber();

    BankAccountType getBankAccountType();

    String getBankDocumentReference();

}
