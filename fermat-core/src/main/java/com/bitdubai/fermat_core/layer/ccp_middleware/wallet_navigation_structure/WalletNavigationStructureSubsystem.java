package com.bitdubai.fermat_core.layer.ccp_middleware.wallet_navigation_structure;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.ccp_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.ccp_middleware.MiddlewareSubsystem;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.navigation_structure.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by natalia on 07/08/15.
 */
public class WalletNavigationStructureSubsystem implements MiddlewareSubsystem {

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
