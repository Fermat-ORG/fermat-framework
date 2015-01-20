package com.bitdubai.smartwallet.platform.layer._5_crypto_network.bitcoin.developer.bitdubai;

import com.bitdubai.smartwallet.platform.layer._5_crypto_network.CryptoNetwork;
import com.bitdubai.smartwallet.platform.layer._5_crypto_network.CryptoNetworkAccessDeveloper;
import com.bitdubai.smartwallet.platform.layer._5_crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetwork;

/**
 * Created by ciencias on 20.01.15.
 */
public class DeveloperBitDubai implements CryptoNetworkAccessDeveloper  {

    CryptoNetwork mCryptoNetwork;

    @Override
    public CryptoNetwork getCryptoNetwork() {
        return mCryptoNetwork;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to run. Now there is only one, so
         * it is easy to choose.
         */

        mCryptoNetwork = new BitcoinCryptoNetwork();

    }


}
