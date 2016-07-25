package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatVaultEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Enums the crypto currency vaults in Fermat.
 * Created by Leon Acosta (laion.cj91@gmail.com) on 02/09/2015.
 */
public enum CryptoAssetVault implements FermatVaultEnum {

    BITCOIN_ASSET_VAULT("ASSV", CryptoCurrency.BITCOIN),;

    private String code;

    private CryptoCurrency cryptoCurrency;

    CryptoAssetVault(String code, CryptoCurrency cryptoCurrency) {
        this.code = code;
        this.cryptoCurrency = cryptoCurrency;
    }


    public static CryptoAssetVault getByCode(String code) throws InvalidParameterException {

        for (CryptoAssetVault vault : CryptoAssetVault.values()) {
            if (vault.getCode().equals(code))
                return vault;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This code is not valid for the CryptoCurrencyVault enum.");
    }

    public static CryptoAssetVault getByCryptoCurrency(CryptoCurrency cryptoCurrency) throws InvalidParameterException {

        for (CryptoAssetVault vault : CryptoAssetVault.values()) {
            if (vault.getCryptoCurrency().equals(cryptoCurrency))
                return vault;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("CryptoCurrency Received: ").append(cryptoCurrency).toString(), "This CryptoCurrency is not valid for the CryptoCurrencyVault enum.");
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public VaultType getVaultType() {
        return VaultType.CRYPTO_ASSET_VAULT;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return this.cryptoCurrency;
    }

}
