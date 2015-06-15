package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.WalletRuntimeModulePluginRoot;

/**
 * Created by loui on 04/02/15.
 */
public class DeveloperBitDubai implements PluginDeveloper {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    public DeveloperBitDubai() {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        plugin = new WalletRuntimeModulePluginRoot();
        
    }

}