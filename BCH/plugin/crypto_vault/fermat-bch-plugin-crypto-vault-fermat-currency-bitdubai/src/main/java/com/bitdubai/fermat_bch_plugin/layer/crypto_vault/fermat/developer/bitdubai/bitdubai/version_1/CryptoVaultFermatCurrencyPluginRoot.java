package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * Created by rodrigo on 6/7/16.
 */
public class CryptoVaultFermatCurrencyPluginRoot extends AbstractPlugin {
    public CryptoVaultFermatCurrencyPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        System.out.println("***FermatVault*** started...");
    }
}
