package com.bitdubai.fermat_core.layer.dmp_actor;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_actor.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.dmp_actor.intra_user.IntraUserSubsystem;



/**
 * Created by natalia on 11/08/15.
 */
public class ActorLayer implements PlatformLayer {

    private Plugin mActorIntraUser;

    public Plugin getActorIntraUser() {
        return mActorIntraUser;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * Start DMP Intra User
         */

        IntraUserSubsystem actorIntraUserSubsystemType = new IntraUserSubsystem();

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
