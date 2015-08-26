package com.bitdubai.fermat_api.layer.dmp_wallet_module.fiat_over_crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.fiat_over_crypto_wallet.interfaces.FiatOverCryptoWalletTransaction;

/**
 * The interface <code>FiatOverCryptoWalletModuleTransaction</code>
 * provides the information to the user interface about a transaction. When the wallet module gets the
 * transactions from the basic wallet it adds information that is given to the user interface, this is
 * the interface that let the UI see the complete information
 */
public interface FiatOverCryptoWalletModuleTransaction {

    /**
     * The method <code>getFiatOverCryptoWalletTransaction</code> returns the information of the
     *
     * @return the information provided by the fiat over crypto basic wallet
     */
    FiatOverCryptoWalletTransaction getFiatOverCryptoWalletTransaction();

    /**
     * The method <code>getSenderActorName</code> returns the alias of the actor who sent the transaction
     *
     * @return the actor alias
     */
    String getSenderActorName();

    /**
     * The method <code>getReceptorActorName</code> returns the alias of the actor who received the transaction
     *
     * @return the actor alias
     */
    String getReceptorActorName();
}
