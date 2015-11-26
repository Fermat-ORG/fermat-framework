package com.bitdubai.fermat_csh_core.layer.wallet;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_csh_core.layer.cash_money_transaction.hold.HoldPluginSubsystem;

/**
 * Created by Alejandro Bicelis on 11/25/2015.
 */
public class WalletLayer extends AbstractLayer {

    public WalletLayer() {
        super(Layers.TRANSACTION);
    }

    public void start() throws CantStartLayerException {

        try {
            registerPlugin(new HoldPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}