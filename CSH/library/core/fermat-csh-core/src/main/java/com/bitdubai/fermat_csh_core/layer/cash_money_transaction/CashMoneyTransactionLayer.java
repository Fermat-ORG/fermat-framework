package com.bitdubai.fermat_csh_core.layer.cash_money_transaction;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_csh_core.layer.cash_money_transaction.hold.HoldPluginSubsystem;
import com.bitdubai.fermat_csh_core.layer.cash_money_transaction.unhold.UnholdPluginSubsystem;

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

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
