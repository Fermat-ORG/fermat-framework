/*
 * @#DeveloperBitDubai.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.NetworkNodePluginRoot;


/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.DeveloperBitDubai</code> this class
 * is responsible to initialize the plugin root
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 11/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {

    /**
     * Represent the plugin instance
     */
    Plugin plugin;

    /**
     * Constructor
     */
    public DeveloperBitDubai() {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */
         plugin = (Plugin) new NetworkNodePluginRoot();

    }

    /**
     * (non-javadoc)
     * @see PluginDeveloper#getPlugin()
     */
    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * (non-javadoc)
     * @see PluginLicensor#getAmountToPay()
     */
    @Override
    public int getAmountToPay() {
        return 100;
    }

    /**
     * (non-javadoc)
     * @see PluginLicensor#getCryptoCurrency()
     */
    @Override
    public CryptoCurrency getCryptoCurrency() {
        return CryptoCurrency.BITCOIN;
    }

    /**
     * (non-javadoc)
     * @see PluginLicensor#getAddress()
     */
    @Override
    public String getAddress() {
        return "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv";
    }

    /**
     * (non-javadoc)
     * @see PluginLicensor#getTimePeriod()
     */
    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY;
    }
}
