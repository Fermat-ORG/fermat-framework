package com.bitdubai.fermat_core.layer.pip_actor;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_pip_api.layer.pip_actor.ActorSubsystem;
import com.bitdubai.fermat_pip_api.layer.pip_actor.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.pip_actor.actor_developer.ActorDeveloperSubsystem;

/**
 * The interface <code>com.bitdubai.fermat_core.layer.pip_actor.CryptoVaultLayer</code>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/06/15.
 * @version 1.0
 */
public class ActorLayer implements PlatformLayer {

    private Plugin mActorDeveloper;

    public Plugin getActorDeveloper() {
        return mActorDeveloper;
    }

    @Override
    public void start() throws CantStartLayerException {

        ActorSubsystem actorDeveloperSubsystemType = new ActorDeveloperSubsystem();

        try {
            actorDeveloperSubsystemType.start();
            mActorDeveloper = (actorDeveloperSubsystemType).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartActorLayerException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }
    }
}
