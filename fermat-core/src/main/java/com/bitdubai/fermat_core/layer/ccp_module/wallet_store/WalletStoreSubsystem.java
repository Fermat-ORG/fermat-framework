package com.bitdubai.fermat_core.layer.ccp_module.wallet_store;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.dmp_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_module.ModuleSubsystem;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 05/04/15.
 */
public class WalletStoreSubsystem implements ModuleSubsystem {

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
            plugin =  developer.getPlugin();

        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }

}
