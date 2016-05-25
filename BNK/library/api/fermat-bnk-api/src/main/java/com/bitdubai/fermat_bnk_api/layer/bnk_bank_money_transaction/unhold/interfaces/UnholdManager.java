package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantMakeUnholdTransactionException;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by memo on 17/11/15.
 */
public interface UnholdManager extends FermatManager, Serializable {

    BankTransaction unHold(BankTransactionParameters parameters)throws CantMakeUnholdTransactionException;

    BankTransactionStatus getUnholdTransactionsStatus(UUID transactionId) throws CantGetUnholdTransactionException;

    boolean isTransactionRegistered(UUID transactionId);
}
