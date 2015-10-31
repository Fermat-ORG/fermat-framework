package com.bitdubai.fermat_core.layer.cry_crypto_router.incoming_crypto;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_cry_api.layer.crypto_router.CantStartSubsystemException;
import com.bitdubai.fermat_cry_api.layer.crypto_router.CryptoRouterSubsystem;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.DeveloperBitDubaiOld;

/**
 * Created by loui on 18/03/15.
 */
public class IncomingCryptoSubsysten implements CryptoRouterSubsystem {

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
