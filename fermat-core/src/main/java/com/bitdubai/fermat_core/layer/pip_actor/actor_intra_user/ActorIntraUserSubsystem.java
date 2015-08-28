package com.bitdubai.fermat_core.layer.pip_actor.actor_intra_user;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_pip_api.layer.pip_actor.ActorSubsystem;
import com.bitdubai.fermat_pip_api.layer.pip_actor.CantStartSubsystemException;

/**
 * Created by natalia on 31/07/15.
 */
public class ActorIntraUserSubsystem implements ActorSubsystem {

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
