/*
* @#WsCommunicationsNetworkClientPluginRoot.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_2.WsCommunicationsNetworkClientPluginRoot;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_2.WsCommunicationsNetworkClientPluginRoot</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 12/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {

    
    Plugin plugin;


    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    public DeveloperBitDubai () {
        plugin = new WsCommunicationsNetworkClientPluginRoot();
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
