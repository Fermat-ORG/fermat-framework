package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;

import java.io.Serializable;


/**
 * Created by memo on 17/11/15.
 */
public interface DepositManager extends FermatManager, Serializable {
    BankTransaction makeDeposit(BankTransactionParameters parameters) throws CantMakeDepositTransactionException;
}
