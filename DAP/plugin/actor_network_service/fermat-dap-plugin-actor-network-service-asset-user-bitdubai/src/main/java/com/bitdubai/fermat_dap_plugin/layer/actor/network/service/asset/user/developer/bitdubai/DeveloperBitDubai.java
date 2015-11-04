/*
* @#DeveloperBitDubai.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.AssetUserActorNetworkServicePluginRoot;

/**
* The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.DeveloperBitDubai</code> is
* the responsible to initialize the plugin.
* <p/>
*
* Created by Roberto Requena - (rrequena) on 17/07/15.
*
* @version 1.0
*/
public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {

    Plugin plugin;

    public DeveloperBitDubai() {
    plugin = new AssetUserActorNetworkServicePluginRoot();
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
