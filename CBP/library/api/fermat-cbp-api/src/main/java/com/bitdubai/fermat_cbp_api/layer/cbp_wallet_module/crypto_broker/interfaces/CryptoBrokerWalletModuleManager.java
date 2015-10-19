package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;

/**
 * Created by angel on 17/9/15.
 */
public interface CryptoBrokerWalletModuleManager extends ModuleManager {

    /**
     * @param walletPublicKey the public key of the wallet
     * @return an interface the contain the methods to manipulate the selected wallet
     */
    Wallet getCryptoCustomerWallet(String walletPublicKey);

}
