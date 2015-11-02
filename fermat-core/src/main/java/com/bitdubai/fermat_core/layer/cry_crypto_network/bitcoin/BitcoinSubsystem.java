package com.bitdubai.fermat_core.layer.cry_crypto_network.bitcoin;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_cry_api.layer.crypto_network.CantStartSubsystemException;
import com.bitdubai.fermat_cry_api.layer.crypto_network.CryptoNetworkSubsystem;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.DeveloperBitDubaiOld;

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
            DeveloperBitDubaiOld developerBitDubaiOld = new DeveloperBitDubaiOld();
            plugin = developerBitDubaiOld.getPlugin();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
