package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;

/**
 * Created by loui on 18/03/15.
 * Modified by Arturo Vallone 25/04/2015
 */
public class DeveloperBitDubaiOld implements PluginDeveloper, PluginLicensor {

    /**
     * PluginDeveloper Interface member variables.
     */
    Plugin plugin;


    public DeveloperBitDubaiOld() {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */
        plugin = new IncomingCryptoTransactionPluginRoot();

    }


    /**
     *PluginDeveloper Interface implementation.
     */
    @Override
    public Plugin getPlugin() {
        return plugin;
    }


    /**
     *PluginLicensor Interface implementation.
     */
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