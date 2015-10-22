package com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/06/15.
 * @version 1.0
 */
public interface CryptoWalletManager {

    CryptoWallet getCryptoWallet() throws CantGetCryptoWalletException;

}
