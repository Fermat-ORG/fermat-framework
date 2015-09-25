package com.bitdubai.fermat_core.layer.ccp.middleware.wallet_contacts;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_ccp_api.layer.middleware.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_api.layer.middleware.MiddlewareSubsystem;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletContactsSubsystem implements MiddlewareSubsystem {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void start() throws CantStartSubsystemException {

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
