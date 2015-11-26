package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantMakeHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces.HoldManager;

import java.util.UUID;

/**
 * Created by memo on 25/11/15.
 */
public class HoldBankMoneyTransactionPluginRoot extends AbstractPlugin implements HoldManager {
    public HoldBankMoneyTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public BankTransaction hold(BankTransactionParameters parameters) throws CantMakeHoldTransactionException {
        return null;
    }

    @Override
    public BankTransactionStatus getHoldTransactionsStatus(UUID transactionId) throws CantGetHoldTransactionException {
        return null;
    }
}
