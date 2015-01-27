package com.bitdubai.platform.layer._7_crypto_network.bitcoin.developer.bitdubai;

import com.bitdubai.platform.layer._7_crypto_network.CryptoNetworkService;
import com.bitdubai.platform.layer._7_crypto_network.CryptoNetworkDeveloper;
import com.bitdubai.platform.layer._7_crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkService;

/**
 * Created by ciencias on 20.01.15.
 */
public class DeveloperBitDubai implements CryptoNetworkDeveloper {

    CryptoNetworkService mCryptoNetworkService;

    @Override
    public CryptoNetworkService getCryptoNetwork() {
        return mCryptoNetworkService;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to run. Now there is only one, so
         * it is easy to choose.
         */

        mCryptoNetworkService = new BitcoinCryptoNetworkService();

    }


}
