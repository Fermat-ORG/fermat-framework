package com.bitdubai.fermat_bnk_core.layer.bank_money_transaction;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_bnk_core.layer.bank_money_transaction.deposit.DepositBankMoneyTransactionPluginSubsystem;
import com.bitdubai.fermat_bnk_core.layer.bank_money_transaction.hold.HoldBankMoneyTransactionPluginSubsystem;
import com.bitdubai.fermat_bnk_core.layer.bank_money_transaction.unhold.UnholdBankMoneyTransactionPluginSubsystem;

/**
 * Created by memo on 25/11/15.
 */
public class BankMoneyTransactionLayer extends AbstractLayer {

    public BankMoneyTransactionLayer() {
        super(Layers.TRANSACTION);
    }

    @Override
    public void start() throws CantStartLayerException {
        try {
            registerPlugin(new DepositBankMoneyTransactionPluginSubsystem());
            registerPlugin(new HoldBankMoneyTransactionPluginSubsystem());
            registerPlugin(new UnholdBankMoneyTransactionPluginSubsystem());
            //registerPlugin(new WithdrawBankMoneyTransactionPluginSubsystem());
        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }
}
