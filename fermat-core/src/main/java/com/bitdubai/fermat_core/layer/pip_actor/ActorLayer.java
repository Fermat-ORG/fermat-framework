package com.bitdubai.fermat_core.layer.pip_actor;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.pip_actor.actor_intra_user.ActorIntraUserSubsystem;
import com.bitdubai.fermat_pip_api.layer.pip_actor.ActorSubsystem;
import com.bitdubai.fermat_pip_api.layer.pip_actor.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.pip_actor.actor_developer.ActorDeveloperSubsystem;
import com.bitdubai.fermat_core.layer.pip_actor.actor_extra_user.ActorExtraUserSubsystem;

/**
 * The interface <code>com.bitdubai.fermat_core.layer.pip_actor.ActorLayer</code>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/06/15.
 * @version 1.0
 */
public class ActorLayer implements PlatformLayer {

    private Plugin mActorDeveloper;

    private Plugin mActorExtraUser;

    private Plugin mActorIntraUser;

    public Plugin getmActorDeveloper() {
        return mActorDeveloper;
    }

    public Plugin getmActorExtraUser() {
        return mActorExtraUser;
    }

    public Plugin getmActorIntraUser() {
        return mActorIntraUser;
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

        ActorExtraUserSubsystem actorExtraUserSubsystemType = new ActorExtraUserSubsystem();

        try {
            actorExtraUserSubsystemType.start();
            mActorExtraUser = actorExtraUserSubsystemType.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartActorLayerException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }

        /**
         * Start Intra User
         */

        ActorIntraUserSubsystem actorIntraUserSubsystemType = new ActorIntraUserSubsystem();

        try {
            actorIntraUserSubsystemType.start();
            mActorIntraUser = actorIntraUserSubsystemType.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartActorLayerException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }

    }
}
