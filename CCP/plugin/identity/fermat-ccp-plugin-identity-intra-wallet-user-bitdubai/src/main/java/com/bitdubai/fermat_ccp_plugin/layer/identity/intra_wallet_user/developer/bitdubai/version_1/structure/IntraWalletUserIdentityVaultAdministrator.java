package com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantIdentifyVaultException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraWalletUserIdentityVaultAdministrator</code>
 * is intended to contain all the necessary business logic to decide which vault to use.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraWalletUserIdentityVaultAdministrator {

    private final CryptoVaultManager cryptoVaultManager;

    public IntraWalletUserIdentityVaultAdministrator(final CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }

    public CryptoVaultManager getVault(CryptoCurrencyVault cryptoCurrencyVault) throws CantIdentifyVaultException {
        switch(cryptoCurrencyVault) {
            case BITCOIN_VAULT:
                return cryptoVaultManager;
            default:
                throw new CantIdentifyVaultException("Unexpected CryptoCurrencyVault: "+cryptoCurrencyVault.toString()+" - "+ cryptoCurrencyVault.getCode());
        }
    }
}
