package com.bitdubai.fermat_api.layer.ccp_basic_wallet.fiat_over_crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.ccp_basic_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.ccp_basic_wallet.common.exceptions.CantLoadWalletException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.ccp_basic_wallet.fiat_over_crypto_wallet.interfaces.FiatOverCryptoWalletManager</code>
 * provides the methods to administrate the creation and loading of fiat over crypto wallets
 */
public interface FiatOverCryptoWalletManager {

    /**
     * The method <code>createWallet</code> creates a basic wallet and registers that its external
     * reference of the wallet is the public key given as parameter
     *
     * @param walletPublicKey the public key used by the platform to identify the wallet.
     * @throws CantCreateWalletException
     */
    public void createWallet(String walletPublicKey) throws CantCreateWalletException;

    /**
     * The method <code>loadWallet</code> returns an interface that let us administrate the wallet
     * specified by the wallet public key
     *
     * @param walletPublicKey the public key of the wallet
     * @return an interface that let us administrate the wallet balances and transactions lists
     * @throws CantLoadWalletException
     */
    public FiatOverCryptoWalletWallet loadWallet(String walletPublicKey) throws CantLoadWalletException;
}
