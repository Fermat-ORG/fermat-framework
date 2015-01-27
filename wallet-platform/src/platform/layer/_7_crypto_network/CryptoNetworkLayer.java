package platform.layer._7_crypto_network;

import platform.layer.CantStartLayerException;
import platform.layer.PlatformLayer;
import platform.layer._7_crypto_network.bitcoin.BitcoinSubsystem;
import platform.layer._7_crypto_network.dogecoin.DogecoinSubsystem;
import platform.layer._7_crypto_network.litecoin.LitecoinSubsystem;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 30.12.14.
 */

/**
 * This object initializes each crypto network subsystem and stored those who could be started in a list.
 */


public class CryptoNetworkLayer implements PlatformLayer {

    List<CryptoNetworkSubsystem> mCryptoNetworkSubsystems;

     @Override
    public void start() throws CantStartLayerException {


        mCryptoNetworkSubsystems = new ArrayList<>();
        CryptoNetworkSubsystem cryptoNetworkSubsystem;


        /**
         * Lets see if we have an implementation to access the bitcoin network.
         */
        cryptoNetworkSubsystem = new BitcoinSubsystem();
        try {
            cryptoNetworkSubsystem.start();
            mCryptoNetworkSubsystems.add(cryptoNetworkSubsystem);
        }
        catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());
        }

        /**
         * Lets see if we have an implementation to access the litecoin network.
         */
        cryptoNetworkSubsystem = new LitecoinSubsystem();
        try {
            cryptoNetworkSubsystem.start();
            mCryptoNetworkSubsystems.add(cryptoNetworkSubsystem);
        }
        catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());
        }

        /**
         * Lets see if we have an implementation to access the dogecoin network.
         */
        cryptoNetworkSubsystem = new DogecoinSubsystem();
        try {
            cryptoNetworkSubsystem.start();
            mCryptoNetworkSubsystems.add(cryptoNetworkSubsystem);
        }
        catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());
        }

        if (mCryptoNetworkSubsystems.size() == 0) {
            throw new CantStartLayerException();
        }
    }


    public CryptoNetworkService getCryptoNetwork (CryptoNetworks pCryptoNetwork) {

        CryptoNetworkService cryptoNetworkService = null;

        switch (pCryptoNetwork) {

            case BITCOIN:

                BitcoinSubsystem bitcoinSubsystem = null;

                for (CryptoNetworkSubsystem cryptoNetworkSubsystem : mCryptoNetworkSubsystems)
                {
                    try {
                        if (bitcoinSubsystem == null) {
                            bitcoinSubsystem = (BitcoinSubsystem) cryptoNetworkSubsystem;
                        }
                    }
                    catch (Exception e) {}
                }
                cryptoNetworkService =  bitcoinSubsystem.getCryptoNetwork();

                break;

            case LITECOIN:
                break;
            case DOGECOIN:
                break;
        }

        return cryptoNetworkService;

    }
}
