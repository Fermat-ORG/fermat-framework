package com.bitdubai.fermat_core.layer.wpd.middleware.wallet_manager;

import com.bitdubai.fermat_api.Plugin;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.WPDMiddlewareSubsystem;

import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletManagerSubsystem implements WPDMiddlewareSubsystem {

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
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
