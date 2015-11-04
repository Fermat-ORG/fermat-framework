package com.bitdubai.fermat_core.layer.wpd.sub_app_module.wallet_factory;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.CantStartSubsystemException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.WPDSubAppModuleSubsystem;
import com.fermat_wpd_plugin.layer.sub_app_module.wallet_factory.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 26/04/15.
 */
public class WalletFactorySubsystem implements WPDSubAppModuleSubsystem {

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
            throw new CantStartSubsystemException(e, null, null);
        }
    }

}