package com.bitdubai.fermat_csh_core.layer.wallet_module;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_csh_core.layer.wallet_module.cash_money.WalletModulePluginSubsystem;

/**
 * Created by memo on 07/12/15.
 */
public class WalletModuleLayer extends AbstractLayer {


    public WalletModuleLayer() {
        super(Layers.WALLET_MODULE);
    }

    @Override
    public void start() throws CantStartLayerException {
        try {
            registerPlugin(new WalletModulePluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }
}
