package com.bitdubai.wallet_platform_core.layer._4_user.manager;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.layer._4_user.UserSubsystem;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantStartSubsystemException;
import com.bitdubai.wallet_platform_core.layer._4_user.manager.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by ciencias on 22.01.15.
 */
public class UserManagerSubsystem implements UserSubsystem {

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
