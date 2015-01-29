package com.bitdubai.wallet_platform_core.layer._7_crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CryptoWallet;

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
