package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;

/**
 * Created by angel on 17/9/15.
 */
public interface CryptoBrokerWalletModuleManager extends ModuleManager {

    /**
     * @param walletPublicKey the public key of the wallet
     * @return an interface the contain the methods to manipulate the selected wallet
     * @throws com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerWalletException
     */
    CryptoBrokerWallet getCryptoBrokerWallet(String walletPublicKey) throws com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerWalletException;

}
