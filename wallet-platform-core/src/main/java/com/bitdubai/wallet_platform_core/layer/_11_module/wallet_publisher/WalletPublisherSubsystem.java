package com.bitdubai.wallet_platform_core.layer._11_module.wallet_publisher;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.PluginDeveloper;
import com.bitdubai.wallet_platform_api.layer._11_module.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._11_module.ModuleSubsystem;
import com.bitdubai.wallet_platform_plugin.layer._11_module.wallet_publisher.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 06/02/15.
 */
public class WalletPublisherSubsystem implements ModuleSubsystem {
    
    Plugin plugin;


    @Override
    public Plugin getPlugin() {
        return null;
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
