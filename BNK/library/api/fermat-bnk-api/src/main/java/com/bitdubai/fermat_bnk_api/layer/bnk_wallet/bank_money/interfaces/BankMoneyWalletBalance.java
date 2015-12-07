package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.*;


/**
 * Created by Yordin Alayn on 30.09.15.
 */

public interface BankMoneyWalletBalance {


    double getBalance(String accountNumber)  throws CantCalculateBalanceException;

    void debit(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterDebitException;

    void credit(BankMoneyTransactionRecord bankMoneyTransactionRecord)  throws CantRegisterCreditException;


}
