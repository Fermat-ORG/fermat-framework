package com.bitdubai.fermat_core.layer.pip_actor.actor_developer;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_pip_api.layer.pip_actor.ActorSubsystem;
import com.bitdubai.fermat_pip_api.layer.pip_actor.CantStartSubsystemException;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.DeveloperBitDubai;

/**
 * The interface <code>com.bitdubai.fermat_core.layer.pip_actor.actor_developer.ActorDeveloperSubsystem</code>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/06/15.
 * @version 1.0
 */
public class ActorDeveloperSubsystem implements ActorSubsystem {

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
