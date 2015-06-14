package com.bitdubai.fermat_core.layer._12_world.shape_shift;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer._16_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._12_world.WorldSubsystem;
import com.bitdubai.fermat_dmp_plugin.layer.world.shape_shift.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 12/03/15.
 */
public class ShapeShiftWorldSubsystem implements WorldSubsystem {

    Plugin plugin;

    @Override
    public Plugin getPlugin(){
        return plugin;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }

    }
}
