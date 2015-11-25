package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantMakeHoldTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 17/11/15.
 */
public interface HoldManager {

    BankTransaction hold(BankTransactionParameters parameters)throws CantMakeHoldTransactionException;

    BankTransactionStatus getHoldTransactionsStatus(UUID transactionId) throws CantGetHoldTransactionException;

}
