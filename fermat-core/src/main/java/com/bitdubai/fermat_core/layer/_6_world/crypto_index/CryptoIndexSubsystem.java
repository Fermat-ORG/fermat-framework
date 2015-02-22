package com.bitdubai.fermat_core.layer._6_world.crypto_index;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer._13_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._6_world.WorldSubsystem;
import com.bitdubai.fermat_core.layer._6_world.crypto_index.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 12/02/15.
 */
public class CryptoIndexSubsystem implements WorldSubsystem {
    
    Plugin plugin;
    
    @Override
    public Plugin getPlugin(){
        return plugin;
    }
    
    @Override
    public void start() throws CantStartSubsystemException{
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
