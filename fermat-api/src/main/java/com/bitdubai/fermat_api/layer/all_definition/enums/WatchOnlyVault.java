package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatVaultEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 1/5/16.
 */
public enum WatchOnlyVault implements FermatVaultEnum {

    WATCH_ONLY_VAULT("WOV", CryptoCurrency.BITCOIN),;

    private String code;

    private CryptoCurrency cryptoCurrency;

    WatchOnlyVault(String code, CryptoCurrency cryptoCurrency) {
        this.code = code;
        this.cryptoCurrency = cryptoCurrency;
    }


    public static WatchOnlyVault getByCode(String code) throws InvalidParameterException {

        for (WatchOnlyVault vault : WatchOnlyVault.values()) {
            if (vault.getCode().equals(code))
                return vault;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This code is not valid for the CryptoCurrencyVault enum.");
    }

    public static WatchOnlyVault getByCryptoCurrency(CryptoCurrency cryptoCurrency) throws InvalidParameterException {

        for (WatchOnlyVault vault : WatchOnlyVault.values()) {
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
