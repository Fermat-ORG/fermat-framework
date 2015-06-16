package com.bitdubai.fermat_core.layer.osa_android.android.device_location;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.osa_android.CantStartSubsystemException;
//import com.bitdubai.fermat_pip_addon.layer.osa_android.device_location.developer.bitdubai.DeveloperBitDubai; (No compilaba LUIS)

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
    //        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai(); (No compilaba LUIS)
     //       addon = developerBitDubai.getAddon();(No compilaba LUIS)
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }

}
