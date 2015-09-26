package com.bitdubai.fermat_core.layer.ccp_actor.intra_user;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.dmp_actor.ActorSubsystem;
import com.bitdubai.fermat_api.layer.dmp_actor.CantStartSubsystemException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by natalia on 11/08/15.
 */
public class IntraUserSubsystem implements ActorSubsystem {

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
