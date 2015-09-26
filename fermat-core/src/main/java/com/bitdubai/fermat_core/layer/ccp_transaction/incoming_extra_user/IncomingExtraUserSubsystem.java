package com.bitdubai.fermat_core.layer.ccp_transaction.incoming_extra_user;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.dmp_transaction.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionSubsystem;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 16/02/15.
 */
public class IncomingExtraUserSubsystem implements TransactionSubsystem {

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
            //System.out.println("EXTRA USER SUBSYSTEM SUCCESS");
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }
}
