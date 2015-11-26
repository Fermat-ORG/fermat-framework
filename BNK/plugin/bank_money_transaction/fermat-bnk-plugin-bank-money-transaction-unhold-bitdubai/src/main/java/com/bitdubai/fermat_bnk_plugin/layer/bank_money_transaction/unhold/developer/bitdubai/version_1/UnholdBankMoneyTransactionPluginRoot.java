package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces.HoldManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantMakeUnholdTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.interfaces.UnholdManager;

import java.util.UUID;

/**
 * Created by memo on 25/11/15.
 */
public class UnholdBankMoneyTransactionPluginRoot extends AbstractPlugin implements UnholdManager {
    public UnholdBankMoneyTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public BankTransaction unHold(BankTransactionParameters parameters) throws CantMakeUnholdTransactionException {
        return null;
    }

    @Override
    public BankTransactionStatus getUnholdTransactionsStatus(UUID transactionId) throws CantGetUnholdTransactionException {
        return null;
    }
}
