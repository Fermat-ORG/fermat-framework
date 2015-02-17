package com.bitdubai.wallet_platform_core.layer._10_middleware.bank_notes;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._10_middleware.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._10_middleware.MiddlewareSubsystem;
import com.bitdubai.wallet_platform_core.layer._10_middleware.bank_notes.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 17/02/15.
 */
public class BankNotesSubsystem implements MiddlewareSubsystem {

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
