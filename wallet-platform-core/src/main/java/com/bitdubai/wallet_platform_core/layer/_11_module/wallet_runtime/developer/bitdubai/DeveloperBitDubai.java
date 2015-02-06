package com.bitdubai.wallet_platform_core.layer._11_module.wallet_runtime.developer.bitdubai;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.PluginDeveloper;
import com.bitdubai.wallet_platform_plugin.layer._11_module.wallet_runtime.developer.bitdubai.version_1.WalletRuntimePluginRoot;

/**
 * Created by ciencias on 21.01.15.
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

        plugin = new WalletRuntimePluginRoot();

    }
}
