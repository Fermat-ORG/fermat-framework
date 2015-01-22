package com.bitdubai.smartwallet.platform.layer._6_crypto_network.bitcoin;

import com.bitdubai.smartwallet.platform.layer._6_crypto_network.CantStartSubsystemException;
import com.bitdubai.smartwallet.platform.layer._6_crypto_network.CryptoNetwork;
import com.bitdubai.smartwallet.platform.layer._6_crypto_network.CryptoNetworkSubsystem;
import com.bitdubai.smartwallet.platform.layer._6_crypto_network.bitcoin.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by ciencias on 30.12.14.
 */
public class BitcoinSubsystem implements CryptoNetworkSubsystem {

    CryptoNetwork mCryptoNetwork;

    @Override
    public CryptoNetwork getCryptoNetwork() {
        return mCryptoNetwork;
    }

    @Override
    public void start() throws CantStartSubsystemException {

        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mCryptoNetwork = developerBitDubai.getCryptoNetwork();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
