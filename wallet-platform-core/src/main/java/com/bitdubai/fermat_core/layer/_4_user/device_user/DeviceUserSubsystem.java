package com.bitdubai.fermat_core.layer._4_user.device_user;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer._4_user.UserSubsystem;
import com.bitdubai.fermat_api.layer._4_user.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer._4_user.device_user.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by ciencias on 22.01.15.
 */
public class DeviceUserSubsystem implements UserSubsystem {

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
