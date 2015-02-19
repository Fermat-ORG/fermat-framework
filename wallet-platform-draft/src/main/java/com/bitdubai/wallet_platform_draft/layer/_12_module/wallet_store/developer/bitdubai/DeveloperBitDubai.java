package com.bitdubai.wallet_platform_plugin.layer._13_module.wallet_store.developer.bitdubai;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.PluginDeveloper;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.TimeFrequency;
import com.bitdubai.wallet_platform_api.layer._5_license.PluginLicensor;
import com.bitdubai.wallet_platform_plugin.layer._13_module.wallet_store.developer.bitdubai.version_1.WalletStorePluginRoot;

/**
 * Created by loui on 04/02/15.
 */
public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {

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
         
        plugin = new WalletStorePluginRoot();
        
    }

    @Override
    public int getAmountToPay() {
        return 100;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return CryptoCurrency.BITCOIN;
    }

    @Override
    public String getAddress() {
        return "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv";
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY;
    }
}
