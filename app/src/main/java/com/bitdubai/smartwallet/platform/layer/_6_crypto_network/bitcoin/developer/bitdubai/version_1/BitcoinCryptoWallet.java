package com.bitdubai.smartwallet.platform.layer._6_crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._6_crypto_network.CryptoWallet;

import java.util.UUID;

/**
 * Created by ciencias on 23.01.15.
 */
public class BitcoinCryptoWallet implements CryptoWallet {

    UUID mWalletId;

    public BitcoinCryptoWallet (UUID walletId) {

        mWalletId = walletId;

    }



}
