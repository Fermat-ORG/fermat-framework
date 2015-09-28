package com.bitdubai.fermat_core.layer.ccp.transaction.outgoing.intra_actor;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_ccp_api.layer.transaction.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_api.layer.transaction.TransactionSubsystem;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class OutgoingIntraActorSubsystem implements TransactionSubsystem {

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
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}