package com.bitdubai.fermat_core.layer.pip_user.extra_user;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.pip_actor.ActorSubsystem;
import com.bitdubai.fermat_api.layer.pip_actor.CantStartSubsystemException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 22/02/15.
 */
public class ExtraUserSubsystem implements ActorSubsystem {

    Plugin plugin;







    @Override
    public void start() throws CantStartSubsystemException {

        /**
         * I will choose from the different versions available of this functionality.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin =  developerBitDubai.getPlugin();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }


}
