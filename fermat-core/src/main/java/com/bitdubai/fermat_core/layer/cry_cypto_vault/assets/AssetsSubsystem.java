package com.bitdubai.fermat_core.layer.cry_cypto_vault.assets;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CantStartSubsystemException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultSubsystem;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.DeveloperBitDubai;


/**
 * Created by rodrigo on 8/31/15.
 */
public class AssetsSubsystem implements CryptoVaultSubsystem {
    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }
}
