package com.bitdubai.fermat_core.layer.pip_Identity;


import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.ccp_identity.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.ccp_identity.IdentitySubsystem;
import com.bitdubai.fermat_core.layer.pip_Identity.developer.DeveloperIdentitySubsystem;

/**
 * Created by natalia on 11/08/15.
 */
public class IdentityLayer implements PlatformLayer {

    private Plugin mdeveloperIdentity;

    public Plugin getDeveloperIdentity() {
        return mdeveloperIdentity;
    }

    public void start() throws CantStartLayerException {

        /**
         * Start the Developer identity plugin
         */
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
