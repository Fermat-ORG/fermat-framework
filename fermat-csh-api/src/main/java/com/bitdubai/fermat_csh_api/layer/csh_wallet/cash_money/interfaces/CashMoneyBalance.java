package com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces;

import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantRegisterDebitException;

import java.util.List;

/**
 * Created by Yordin Alayn on 30.09.15.
 */

public interface CashMoneyBalance {

    long getBalance()  throws CantCalculateBalanceException;

    List<CashMoney> getCashMoneyBalancesAvailable() throws CantCalculateBalanceException;

    List<CashMoney> getCashMoneyBalancesBook() throws CantCalculateBalanceException;

    void debit(CashMoneyTransactionRecord CashMoneyTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException;

    void credit(CashMoneyTransactionRecord CashMoneyTransactionRecord, BalanceType balanceType)  throws CantRegisterCreditException;
    
}
