package com.bitdubai.fermat_csh_core.layer.cash_money_transaction;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_csh_core.layer.cash_money_transaction.deposit.DepositPluginSubsystem;
import com.bitdubai.fermat_csh_core.layer.cash_money_transaction.hold.HoldPluginSubsystem;
import com.bitdubai.fermat_csh_core.layer.cash_money_transaction.unhold.UnholdPluginSubsystem;
import com.bitdubai.fermat_csh_core.layer.cash_money_transaction.withdrawal.WithdrawalPluginSubsystem;

/**
 * Created by Alejandro Bicelis on 11/25/2015.
 */

public class CashMoneyTransactionLayer extends AbstractLayer {

    public CashMoneyTransactionLayer() {
        super(Layers.CASH_MONEY_TRANSACTION);
    }

    public void start() throws CantStartLayerException {

        try {
            registerPlugin(new HoldPluginSubsystem());
            registerPlugin(new UnholdPluginSubsystem());
            registerPlugin(new DepositPluginSubsystem());
            registerPlugin(new WithdrawalPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
