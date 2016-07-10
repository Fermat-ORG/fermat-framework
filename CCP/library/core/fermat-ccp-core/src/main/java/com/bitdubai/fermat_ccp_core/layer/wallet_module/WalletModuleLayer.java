package com.bitdubai.fermat_ccp_core.layer.wallet_module;

import com.bitdubai.fermat_ccp_core.layer.wallet_module.fermat_wallet.FermatWalletPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.wallet_module.loss_protected.LossProtectedPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_ccp_core.layer.wallet_module.crypto.CryptoPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletModuleLayer extends AbstractLayer {

    public WalletModuleLayer() {
        super(Layers.WALLET_MODULE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new CryptoPluginSubsystem());
            registerPlugin(new LossProtectedPluginSubsystem());
            //registerPlugin(new FermatWalletPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
