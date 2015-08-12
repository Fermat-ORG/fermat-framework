package com.bitdubai.fermat_core.layer.dmp_identity;


import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.IdentitySubsystem;
import com.bitdubai.fermat_core.layer.dmp_identity.intra_user.IntraUserSubsystem;

/**
 * Created by natalia on 11/08/15.
 */
public class IdentityLayer implements PlatformLayer {


    Plugin intraUser;

    public Plugin getIntraUser() {
        return intraUser;
    }


    public void start() throws CantStartLayerException {


        /**
         * Let's start the Intra User Subsystem;
         */
        IdentitySubsystem intraUserSubsystem = new IntraUserSubsystem();

        try {
            intraUserSubsystem.start();
            intraUser = intraUserSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }
    }
}
