package com.bitdubai.fermat_draft.layer._12_module.wallet_publisher;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer._13_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._13_module.ModuleSubsystem;
import com.bitdubai.fermat_plugin.layer._13_module.wallet_publisher.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 06/02/15.
 */
public class WalletPublisherSubsystem implements ModuleSubsystem {
    
    Plugin plugin;


    @Override
    public Plugin getPlugin() {
        return plugin;
    }
    
    @Override
    public void start() throws CantStartSubsystemException {

        /**
         * I will choose from the different versions available of this functionality. 
         */
        
        try {
            PluginDeveloper developer = new DeveloperBitDubai();
            plugin = developer.getPlugin();            
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw  new CantStartSubsystemException();
        }
    }

}
