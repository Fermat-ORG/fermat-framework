package com.bitdubai.fermat_cry_plugin.layer.cry_1_crypto_network.bitcoin.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_cry_plugin.layer.cry_1_crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

/**
 * Created by ciencias on 20.01.15.
 */
public class DeveloperBitDubai implements PluginDeveloper {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        plugin = new BitcoinCryptoNetworkPluginRoot();

    }


}
