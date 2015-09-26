package com.bitdubai.fermat_core.layer.dmp_transaction.outgoing_intra_user;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.dmp_transaction.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionSubsystem;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 20/02/15.
 */
public class OutgoingIntraUserSubsytem implements TransactionSubsystem {

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
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }
}