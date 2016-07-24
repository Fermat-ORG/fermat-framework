package com.bitdubai.fermat_cbp_core.layer.wallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.wallet.crypto_broker.CryptoBrokerPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by Yordin Alayn on 22.11/15.
 */
public class WalletLayer extends AbstractLayer {

    public WalletLayer() {
        super(Layers.WALLET);
    }

    public void start() throws CantStartLayerException {

        try {
            registerPlugin(new CryptoBrokerPluginSubsystem());
        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
