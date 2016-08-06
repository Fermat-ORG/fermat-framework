package com.bitdubai.fermat_bch_core.layer.crypto_vault;

import com.bitdubai.fermat_api.FermatContext;
import com.bitdubai.fermat_bch_core.layer.crypto_vault.fermat_vault.FermatVaultPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_bch_core.layer.crypto_vault.bitcoin_asset_vault.BitcoinAssetVaultPluginSubsystem;
import com.bitdubai.fermat_bch_core.layer.crypto_vault.bitcoin_vault.BitcoinVaultPluginSubsystem;
import com.bitdubai.fermat_bch_core.layer.crypto_vault.bitcoin_watch_only_vault.BitcoinWatchOnlyVaultPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoVaultLayer extends AbstractLayer {

    public CryptoVaultLayer(FermatContext fermatContext) {
        super(Layers.CRYPTO_VAULT,fermatContext);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new BitcoinAssetVaultPluginSubsystem());
            registerPlugin(new BitcoinVaultPluginSubsystem());
            registerPlugin(new BitcoinWatchOnlyVaultPluginSubsystem());
            //registerPlugin(new FermatVaultPluginSubsystem(getFermatContext()));

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
