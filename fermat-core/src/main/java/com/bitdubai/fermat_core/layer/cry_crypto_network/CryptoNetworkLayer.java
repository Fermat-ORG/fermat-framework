package com.bitdubai.fermat_core.layer.cry_crypto_network;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_cry_api.layer.crypto_network.CantStartSubsystemException;
import com.bitdubai.fermat_cry_api.layer.crypto_network.CryptoNetworkSubsystem;
import com.bitdubai.fermat_cry_api.layer.crypto_network.CryptoNetworks;
import com.bitdubai.fermat_core.layer.cry_crypto_network.bitcoin.BitcoinSubsystem;
/*
import com.bitdubai.wallet_platform_core.layer.crypto_network.dogecoin.DogecoinSubsystem;
import com.bitdubai.wallet_platform_core.layer.crypto_network.litecoin.LitecoinSubsystem;
*/

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 30.12.14.
 */

/**
 * This object initializes each crypto network subsystem and stored those who could be started in a list.
 */


public class CryptoNetworkLayer implements PlatformLayer {

    List<CryptoNetworkSubsystem> plugin;





     @Override
    public void start() throws CantStartLayerException {


        plugin = new ArrayList<>();
        CryptoNetworkSubsystem cryptoNetworkSubsystem;


        /**
         * Lets see if we have an implementation to access the bitcoin network.
         */
        cryptoNetworkSubsystem = new BitcoinSubsystem();
        try {
            cryptoNetworkSubsystem.start();
            plugin.add(cryptoNetworkSubsystem);
        }
        catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());
        }

        /**
         * Lets see if we have an implementation to access the litecoin network.
         */
        /*
        cryptoNetworkSubsystem = new LitecoinSubsystem();
        try {
            cryptoNetworkSubsystem.start();
            plugin.add(cryptoNetworkSubsystem);
        }
        catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessageContent());
        }
         */
         
        /**
         * Lets see if we have an implementation to access the dogecoin network.
         */
         /*
        cryptoNetworkSubsystem = new DogecoinSubsystem();
        try {
            cryptoNetworkSubsystem.start();
            plugin.add(cryptoNetworkSubsystem);
        }
        catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessageContent());
        }

        if (plugin.size() == 0) {
            throw new CantStartLayerException();
        }
         */
    }


    public Plugin getCryptoNetwork (CryptoNetworks pCryptoNetwork) {

        Plugin cryptoNetworkPlugin = null;

        switch (pCryptoNetwork) {

            case BITCOIN:

                BitcoinSubsystem bitcoinSubsystem = null;

                for (CryptoNetworkSubsystem cryptoNetworkSubsystem : plugin)
                {
                    try {
                        if (bitcoinSubsystem == null) {
                            bitcoinSubsystem = (BitcoinSubsystem) cryptoNetworkSubsystem;
                        }
                    }
                    catch (Exception e) {}
                }
                cryptoNetworkPlugin =  bitcoinSubsystem.getPlugin();

                break;

            case LITECOIN:
                break;
            case DOGECOIN:
                break;
        }

        return cryptoNetworkPlugin;

    }
}
