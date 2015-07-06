package com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.CryptoWallet</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/06/15.
 * @version 1.0
 */
public interface CryptoWalletManager {

    CryptoWallet getCryptoWallet() throws CantGetCryptoWalletException;

}
