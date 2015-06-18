package com.bitdubai.fermat_core.layer.pip_user.intra_user;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.pip_user.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.pip_user.UserSubsystem;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 22/02/15.
 */
public class IntraUserSubsystem implements UserSubsystem {

    Addon addon;





    @Override
    public Addon getAddon() {
        return addon;
    }





    @Override
    public void start() throws CantStartSubsystemException {

        /**
         * I will choose from the different versions available of this functionality.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            addon =  developerBitDubai.getAddon();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
