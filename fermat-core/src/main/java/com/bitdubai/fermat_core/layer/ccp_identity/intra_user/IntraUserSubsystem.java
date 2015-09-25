package com.bitdubai.fermat_core.layer.ccp_identity.intra_user;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.ccp_identity.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.ccp_identity.IdentitySubsystem;


import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 22/02/15.
 */
public class IntraUserSubsystem implements IdentitySubsystem {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }


    @Override
    public void start() throws CantStartSubsystemException {

        /**
         * I will choose from the different versions available of this functionality.
         */

        try {
            PluginDeveloper developer = new DeveloperBitDubai();
            plugin =  developer.getPlugin();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
