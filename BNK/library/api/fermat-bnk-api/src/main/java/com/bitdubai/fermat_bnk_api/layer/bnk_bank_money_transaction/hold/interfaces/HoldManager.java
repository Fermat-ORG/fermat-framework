package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantMakeHoldTransactionException;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by memo on 17/11/15.
 */
public interface HoldManager extends FermatManager, Serializable {

    BankTransaction hold(BankTransactionParameters parameters)throws CantMakeHoldTransactionException;

    BankTransactionStatus getHoldTransactionsStatus(UUID transactionId) throws CantGetHoldTransactionException;

    boolean isTransactionRegistered(UUID transactionId);

}
