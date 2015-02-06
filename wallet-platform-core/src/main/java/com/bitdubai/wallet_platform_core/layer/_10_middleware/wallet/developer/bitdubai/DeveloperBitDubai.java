package com.bitdubai.wallet_platform_core.layer._10_middleware.wallet.developer.bitdubai;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.PluginDeveloper;
import com.bitdubai.wallet_platform_core.layer._10_middleware.wallet.developer.bitdubai.version_1.WalletMiddewareEngine;

/**
 * Created by ciencias on 20.01.15.
 */
public class DeveloperBitDubai implements PluginDeveloper {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return null;
    }

    public DeveloperBitDubai() {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        plugin = new WalletMiddewareEngine();

    }

}
