package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.exceptions.CantMakeWithdrawTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.interfaces.WithdrawManager;

/**
 * Created by memo on 25/11/15.
 */
public class WithdrawBankMoneyTransactionPluginRoot extends AbstractPlugin implements WithdrawManager {

    public WithdrawBankMoneyTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));

    }

    @Override
    public BankTransaction makeWithdraw(BankTransactionParameters parameters) throws CantMakeWithdrawTransactionException {
        return null;
    }
}
