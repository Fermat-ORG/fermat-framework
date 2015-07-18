package com.bitdubai.fermat_core.layer.pip_hardware.remote_device;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_pip_api.layer.pip_hardware.CantStartSubsystemException;
import com.bitdubai.fermat_pip_api.layer.pip_hardware.HardwareSubsystem;
import com.bitdubai.fermat_pip_addon.layer.hardware.remote_device.remote_device.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 05/03/15.
 */
public class RemoteDeviceHardwareSubsystem implements HardwareSubsystem {

    private Addon addon;





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
            addon = developerBitDubai.getAddon();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }

}
