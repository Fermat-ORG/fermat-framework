package com.bitdubai.fermat_api.layer.dmp_wallet_module.fiat_over_crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_wallet_module.fiat_over_crypto_wallet.exceptions.CantGetFiatOverCryptoWalletModule;

/**
 * The interface <code>FiatOverCryptoWalletManager</code>
 * provides the methods to administrate a wallet module. Remember that a wallet module
 */
public interface FiatOverCryptoWalletManager {
    public FiatOverCryptoWalletModule getFiatOverCryptoWallet() throws CantGetFiatOverCryptoWalletModule;
}
