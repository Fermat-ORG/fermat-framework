package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatVaultEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault</code>
 * Enums the crypto currency vaults in Fermat.
 * Created by Leon Acosta (laion.cj91@gmail.com) on 02/09/2015.
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 30/11/2015.
 */
public enum CryptoCurrencyVault implements FermatVaultEnum {


    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    BITCOIN_VAULT("BITV", CryptoCurrency.BITCOIN),
    FERMAT_VAULT("FERV", CryptoCurrency.FERMAT);

    private final String code;

    private final CryptoCurrency cryptoCurrency;

    CryptoCurrencyVault(String code, CryptoCurrency cryptoCurrency) {
        this.code = code;
        this.cryptoCurrency = cryptoCurrency;
    }

    public static CryptoCurrencyVault getByCode(String code) throws InvalidParameterException {

        for (CryptoCurrencyVault vault : CryptoCurrencyVault.values()) {
            if (vault.getCode().equals(code))
                return vault;
        }
        throw new InvalidParameterException(
                new StringBuilder().append("Code Received: ").append(code).toString(),
                "This code is not valid for the CryptoCurrencyVault enum."
        );
    }

    public static CryptoCurrencyVault getByCryptoCurrency(CryptoCurrency cryptoCurrency) throws InvalidParameterException {

        for (CryptoCurrencyVault vault : CryptoCurrencyVault.values()) {
            if (vault.getCryptoCurrency().equals(cryptoCurrency))
                return vault;
        }
        throw new InvalidParameterException(
                new StringBuilder().append("CryptoCurrency Received: ").append(cryptoCurrency).toString(),
                "This CryptoCurrency is not valid for the CryptoCurrencyVault enum."
        );
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public VaultType getVaultType() {
        return VaultType.CRYPTO_CURRENCY_VAULT;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return this.cryptoCurrency;
    }
}
