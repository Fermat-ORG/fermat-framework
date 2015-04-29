package com.bitdubai.fermat_core.layer._2_os.android.device_location;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer._2_os.CantStartSubsystemException;
import com.bitdubai.fermat_pip_addon.layer._2_os.device_location.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 29/04/15.
 */
public class DeviceLocationSubsystem  {

    Addon addon;




    public Addon getAddon() {
        return addon;
    }




    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            addon = developerBitDubai.getAddon();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }

}
