package com.bitdubai.fermat_core.layer.pip_actor.actor_extra_user;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_pip_api.layer.pip_actor.ActorSubsystem;
import com.bitdubai.fermat_pip_api.layer.pip_actor.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

/**
 * The interface <code>com.bitdubai.fermat_core.layer.pip_actor.actor_extra_user.ActorExtraUserSubsystem</code>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * @version 1.0
 */
public class ActorExtraUserSubsystem implements ActorSubsystem {

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
