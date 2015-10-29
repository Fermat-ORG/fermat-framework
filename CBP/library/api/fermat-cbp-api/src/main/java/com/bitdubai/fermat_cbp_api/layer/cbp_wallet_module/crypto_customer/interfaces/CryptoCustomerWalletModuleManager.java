package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.interfaces;

/**
 * Created by angel on 17/9/15.
 */
public interface CryptoCustomerWalletModuleManager {

    /**
     * @param walletPublicKey the public key of the wallet
     * @return an interface the contain the methods to manipulate the selected wallet
     */
    CryptoCustomerWallet getCryptoCustomerWallet(String walletPublicKey);
}
