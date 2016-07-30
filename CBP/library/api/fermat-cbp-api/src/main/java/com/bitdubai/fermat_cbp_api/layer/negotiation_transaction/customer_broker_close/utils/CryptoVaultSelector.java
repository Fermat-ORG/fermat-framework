package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatVaultEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.PlatformCryptoVault;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantIdentifyVaultException;

/**
 * Created by Yordin Alayn  on 27.12.15.
 */
public class CryptoVaultSelector {

    private CryptoVaultManager cryptoVaultManager;
    private AssetVaultManager assetVaultManager;

    public CryptoVaultSelector(final CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }

    public final PlatformCryptoVault getVault(final VaultType vaultType, final CryptoCurrency cryptoCurrency) throws CantIdentifyVaultException {

        try {

            switch (vaultType) {

                case CRYPTO_CURRENCY_VAULT:
                    return getCryptoCurrencyVault(cryptoCurrency);
                default:
                    throw new CantIdentifyVaultException("CBP-NEGOTIATION TRANSACTION-CUSTOMER BROKER CLOSE. Unexpected vaultType: " + vaultType.toString() + " - " + vaultType.getCode());

            }

        } catch (InvalidParameterException e) {
            throw new CantIdentifyVaultException(e, "CBP-NEGOTIATION TRANSACTION-CUSTOMER BROKER CLOSE. VaultType: " + vaultType.toString() + " - CryptoCurrency: " + cryptoCurrency, "Vault not supported or incorrect data given.");
        }
    }

    public final PlatformCryptoVault getCryptoCurrencyVault(final CryptoCurrency cryptoCurrency) throws InvalidParameterException {

        switch (CryptoCurrencyVault.getByCryptoCurrency(cryptoCurrency)) {

            case BITCOIN_VAULT:
                return cryptoVaultManager;
            default:
                throw new InvalidParameterException("CBP-NEGOTIATION TRANSACTION-CUSTOMER BROKER CLOSE. Unexpected cryptoCurrency: " + cryptoCurrency.toString() + " - " + cryptoCurrency.getCode());

        }
    }

    public final FermatVaultEnum getVaultEnum(final VaultType vaultType, final CryptoCurrency cryptoCurrency) throws InvalidParameterException {

        switch (vaultType) {

            case CRYPTO_CURRENCY_VAULT:
                return CryptoCurrencyVault.getByCryptoCurrency(cryptoCurrency);
            default:
                throw new InvalidParameterException("CBP-NEGOTIATION TRANSACTION-CUSTOMER BROKER CLOSE. VaultType: " + vaultType.toString() + " - CryptoCurrency: " + cryptoCurrency, "Vault not recognized.");

        }
    }
}
