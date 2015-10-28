package com.bitdubai.fermat_core.layer.pip_platform_service.platform_info;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.*;
import com.bitdubai.fermat_pip_api.layer.platform_service.CantStartSubsystemException;
import com.bitdubai.fermat_pip_api.layer.platform_service.PlatformServiceSubsystem;

/**
 * Created by natalia on 29/07/15.
 */
public class PlatformInfoSubsystem implements PlatformServiceSubsystem {

    Addon addon;





    @Override
    public Addon getAddon() {
        return addon;
    }





    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubaiOld developerBitDubai = new DeveloperBitDubaiOld();
            addon = developerBitDubai.getAddon();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
