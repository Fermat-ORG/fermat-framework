package com.bitdubai.fermat_core.layer.pip_identity;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_pip_api.layer.pip_identity.IdentitySubsystem;
import com.bitdubai.fermat_pip_api.layer.pip_identity.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.pip_identity.developer.DeveloperIdentitySubsystem;

/**
 * The interface <code>com.bitdubai.fermat_core.layer.pip_identity.IdentityLayer</code>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * @version 1.0
 */
public class IdentityLayer implements PlatformLayer {

    private Plugin mdeveloperIdentity;

    public Plugin getMdeveloperIdentity() {
        return mdeveloperIdentity;
    }

    @Override
    public void start() throws CantStartLayerException {

        IdentitySubsystem developerIdentitySubsystem = new DeveloperIdentitySubsystem();

        try {
            developerIdentitySubsystem.start();
            mdeveloperIdentity = (developerIdentitySubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartActorLayerException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }
    }
}
