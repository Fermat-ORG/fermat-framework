package com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces;

import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantRegisterDebitException;

/**
 * Created by Yordin Alayn on 30.09.15.
 */

public interface CashMoneyBalance {

    long getBalance()  throws CantCalculateBalanceException;

    void debit(CashMoneyBalanceRecord CashMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterDebitException;

    void credit(CashMoneyBalanceRecord CashMoneyBalanceRecord, BalanceType balanceType)  throws CantRegisterCreditException;
    
}
