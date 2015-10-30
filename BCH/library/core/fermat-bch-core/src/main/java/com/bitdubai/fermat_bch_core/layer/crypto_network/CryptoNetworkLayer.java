package com.bitdubai.fermat_bch_core.layer.crypto_network;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_bch_core.layer.crypto_network.bitcoin_network.BitcoinNetworkPluginSubsystem;
import com.bitdubai.fermat_bch_core.layer.crypto_network.old_bitcoin_network.OldBitcoinNetworkPluginSubsystem;
import com.bitdubai.fermat_bch_core.layer.crypto_vault.bitcoin_vault.BitcoinVaultPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoNetworkLayer extends AbstractLayer {

    public CryptoNetworkLayer() {
        super(Layers.CRYPTO_NETWORK);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new BitcoinNetworkPluginSubsystem());
            registerPlugin(new OldBitcoinNetworkPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
