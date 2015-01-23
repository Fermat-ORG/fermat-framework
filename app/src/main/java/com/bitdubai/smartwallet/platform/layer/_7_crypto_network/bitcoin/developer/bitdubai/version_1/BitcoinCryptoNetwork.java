package com.bitdubai.smartwallet.platform.layer._7_crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._7_crypto_network.CryptoNetwork;
import com.bitdubai.smartwallet.platform.layer._7_crypto_network.CryptoWallet;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * Hi! I am the interface with the bitcoin network. I will provide you with all you need to operate with bitcoins.
 */

public class BitcoinCryptoNetwork implements CryptoNetwork {

    CryptoWallet mCryptoWallet;

    @Override
    public void loadCryptoWallet(UUID walletId) {

        mCryptoWallet = new BitcoinCryptoWallet(walletId);

    }
}
