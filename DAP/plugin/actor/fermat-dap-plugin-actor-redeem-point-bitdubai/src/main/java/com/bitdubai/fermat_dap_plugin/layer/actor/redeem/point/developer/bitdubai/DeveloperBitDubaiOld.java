package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.RedeemPointPluginRoot;

/**
 * Created by Nerio on 09/09/15.
 */
public class DeveloperBitDubaiOld implements PluginDeveloper, PluginLicensor {

    Plugin plugin;


    public DeveloperBitDubaiOld() {
        plugin = new RedeemPointPluginRoot();
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
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
