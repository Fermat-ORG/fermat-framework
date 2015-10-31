package com.bitdubai.fermat_core.layer.cry_cypto_vault.bitcoin;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CantStartSubsystemException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultSubsystem;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.DeveloperBitDubaiOld;

/**
 * Created by loui on 08/06/15.
 */
public class BitcoinSubsystem implements CryptoVaultSubsystem {

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
            DeveloperBitDubaiOld developerBitDubaiOld = new DeveloperBitDubaiOld();
            plugin = developerBitDubaiOld.getPlugin();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }

}
