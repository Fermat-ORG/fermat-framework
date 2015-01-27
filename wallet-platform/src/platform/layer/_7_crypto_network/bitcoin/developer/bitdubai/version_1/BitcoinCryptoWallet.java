package platform.layer._7_crypto_network.bitcoin.developer.bitdubai.version_1;

import platform.layer._7_crypto_network.CryptoWallet;

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
