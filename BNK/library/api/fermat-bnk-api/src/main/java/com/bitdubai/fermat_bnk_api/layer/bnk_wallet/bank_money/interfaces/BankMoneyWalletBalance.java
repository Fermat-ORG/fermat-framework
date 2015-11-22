package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterDebitException;


/**
 * Created by Yordin Alayn on 30.09.15.
 */

public interface BankMoneyWalletBalance {

    //change to BankMoneyWalletBalance

    double getBalance()  throws CantCalculateBalanceException;

    void debit(BankMoneyBalanceRecord BankMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterDebitException;

    void credit(BankMoneyBalanceRecord BankMoneyBalanceRecord, BalanceType balanceType)  throws CantRegisterCreditException;
    
}
