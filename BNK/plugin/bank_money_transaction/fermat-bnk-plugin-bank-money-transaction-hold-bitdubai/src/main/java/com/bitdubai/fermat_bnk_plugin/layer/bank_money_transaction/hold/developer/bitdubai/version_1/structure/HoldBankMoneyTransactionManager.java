package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantMakeHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces.HoldManager;

import java.util.UUID;

/**
 * Created by memo on 25/11/15.
 */
public class HoldBankMoneyTransactionManager implements HoldManager {
    @Override
    public BankTransaction hold(BankTransactionParameters parameters) throws CantMakeHoldTransactionException {
        return null;
    }

    @Override
    public BankTransactionStatus getHoldTransactionsStatus(UUID transactionId) throws CantGetHoldTransactionException {
        return null;
    }
}
