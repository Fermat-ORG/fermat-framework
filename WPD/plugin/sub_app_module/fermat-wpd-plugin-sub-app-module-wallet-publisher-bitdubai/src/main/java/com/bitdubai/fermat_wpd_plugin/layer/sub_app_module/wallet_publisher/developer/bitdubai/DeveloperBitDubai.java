package com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_publisher.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_publisher.developer.bitdubai.version_1.WalletPublisherModuleModulePluginRootPlugin;

/**
 * Created by loui on 05/02/15.
 */
public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {

    Plugin plugin;
    
    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    public DeveloperBitDubai() {
        plugin = new WalletPublisherModuleModulePluginRootPlugin();
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
