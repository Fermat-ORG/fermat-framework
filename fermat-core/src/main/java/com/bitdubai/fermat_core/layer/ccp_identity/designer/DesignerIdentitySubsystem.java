package com.bitdubai.fermat_core.layer.ccp_identity.designer;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.ccp_identity.IdentitySubsystem;
import com.bitdubai.fermat_api.layer.ccp_identity.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.designer.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by natalia on 04/08/15.
 */
public class DesignerIdentitySubsystem implements IdentitySubsystem {

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
