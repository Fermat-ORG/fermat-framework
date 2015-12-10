package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;

import java.util.List;

/**
 * Created by angel on 17/9/15.
 */
public interface CryptoCustomerWalletSettings {

    /**
     * @return the cystomer identity data
     */
    ActorIdentity getAssociatedIdentity();

    /**
     * @return list of wallets that can be associated with the crypto customer wallet
     */
    List<String> getAssociatedWallets();

    /**
     * @return list of currencies the crypto customer is interested
     */
    List<String> getCurrenciesOfInterest();

    /**
     * @return list of bank accounts
     */
    List<String> getBankAccounts();

    /**
     * @return list of location's address
     */
    List<String> getLocations();
}
