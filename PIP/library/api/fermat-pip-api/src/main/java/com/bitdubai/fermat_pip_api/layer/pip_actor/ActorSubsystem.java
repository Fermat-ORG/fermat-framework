package com.bitdubai.fermat_pip_api.layer.pip_actor;

import com.bitdubai.fermat_api.Plugin;

/**
 * The interface <code>com.bitdubai.fermat_pip_api.layer.pip_actor.ActorSubsystem</code>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/06/15.
 * @version 1.0
 */
public interface ActorSubsystem {
    public void start() throws CantStartSubsystemException;
    public Plugin getPlugin();
}
