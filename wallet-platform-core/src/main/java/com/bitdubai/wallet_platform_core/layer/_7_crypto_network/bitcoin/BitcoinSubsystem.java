package com.bitdubai.wallet_platform_core.layer._7_crypto_network.bitcoin;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CryptoNetworkSubsystem;
import com.bitdubai.wallet_platform_plugin.layer._7_crypto_network.bitcoin.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by ciencias on 30.12.14.
 */
public class BitcoinSubsystem implements CryptoNetworkSubsystem {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void start() throws CantStartSubsystemException {

        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
