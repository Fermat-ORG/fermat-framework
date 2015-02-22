package com.bitdubai.fermat_api.layer._7_crypto_network;

import java.util.UUID;

/**
 * Created by ciencias on 26.01.15.
 */
public interface CryptoNetwork {

    public void loadCryptoWallet (UUID walletId);

    public void createCryptoWallet (UUID walletId) throws  CantCreateCryptoWalletException;
}
