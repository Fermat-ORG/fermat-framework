package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.exceptions.CantMakeWithdrawTransactionException;

import java.io.Serializable;


/**
 * Created by memo on 17/11/15.
 */
public interface WithdrawManager extends FermatManager, Serializable {
    BankTransaction makeWithdraw(BankTransactionParameters parameters) throws CantMakeWithdrawTransactionException;
}
