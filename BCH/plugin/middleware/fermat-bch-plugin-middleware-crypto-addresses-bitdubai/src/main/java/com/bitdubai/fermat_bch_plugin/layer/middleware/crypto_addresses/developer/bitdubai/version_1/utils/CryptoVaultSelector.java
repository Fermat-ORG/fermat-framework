package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoAssetVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatVaultEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.PlatformCryptoVault;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantIdentifyVaultException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;

/**
 * The class <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.CryptoVaultSelector</code>
 * is intended to contain all the necessary business logic to decide which vault to use.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 *
 * @version 1.0platformCryptoVault
 * @since Java JDK 1.7
 */
public final class CryptoVaultSelector {

    private CryptoVaultManager cryptoVaultManager;
    private AssetVaultManager assetVaultManager;


    public CryptoVaultSelector(final CryptoVaultManager cryptoVaultManager,
                               final AssetVaultManager  assetVaultManager ) {
        this.cryptoVaultManager = cryptoVaultManager;
        this.assetVaultManager = assetVaultManager;
    }

    public final PlatformCryptoVault getVault(final VaultType      vaultType     ,
                                              final CryptoCurrency cryptoCurrency) throws CantIdentifyVaultException {

        try {
            switch (vaultType) {

                case CRYPTO_ASSET_VAULT:    return getAssetVault(cryptoCurrency);
                case CRYPTO_CURRENCY_VAULT: return getCryptoCurrencyVault(cryptoCurrency);

                default:
                    throw new CantIdentifyVaultException(
                            "Unexpected vaultType: " + vaultType.toString() + " - " + vaultType.getCode()
                    );
            }
        } catch(InvalidParameterException e) {

            throw new CantIdentifyVaultException(e, "VaultType: "+vaultType.toString()+ " - CryptoCurrency: "+ cryptoCurrency, "Vault not supported or incorrect data given.");
        }
    }

    public final PlatformCryptoVault getCryptoCurrencyVault(final CryptoCurrency cryptoCurrency) throws InvalidParameterException {

        switch (CryptoCurrencyVault.getByCryptoCurrency(cryptoCurrency)) {
            case BITCOIN_VAULT: return cryptoVaultManager;

            default:
                throw new InvalidParameterException(
                        "Unexpected cryptoCurrency: " + cryptoCurrency.toString() + " - " + cryptoCurrency.getCode()
                );
        }
    }

    public final PlatformCryptoVault getAssetVault(final CryptoCurrency cryptoCurrency) throws InvalidParameterException {

        switch (CryptoAssetVault.getByCryptoCurrency(cryptoCurrency)) {

            case BITCOIN_ASSET_VAULT: return assetVaultManager;

            default:
                throw new InvalidParameterException(
                        "Unexpected cryptoCurrency: " + cryptoCurrency.toString() + " - " + cryptoCurrency.getCode()
                );
        }
    }

    public final FermatVaultEnum getVaultEnum(final VaultType      vaultType     ,
                                              final CryptoCurrency cryptoCurrency) throws InvalidParameterException {

        switch (vaultType) {

            case CRYPTO_CURRENCY_VAULT:
                return CryptoCurrencyVault.getByCryptoCurrency(cryptoCurrency);
            case CRYPTO_ASSET_VAULT:
                return CryptoAssetVault.getByCryptoCurrency(cryptoCurrency);

            default: throw new InvalidParameterException("VaultType: "+vaultType.toString()+ " - CryptoCurrency: "+ cryptoCurrency, "Vault not recognized.");
        }

    }
}
