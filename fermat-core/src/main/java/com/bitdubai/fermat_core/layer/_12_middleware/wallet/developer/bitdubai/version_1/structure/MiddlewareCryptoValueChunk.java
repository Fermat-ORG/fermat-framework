package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.CryptoValueChunk;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;

/**
 * Created by ciencias on 2/15/15.
 */
public class MiddlewareCryptoValueChunk implements CryptoValueChunk {

    Double cryptoAmount;
    Double fiatAmount;
    CryptoCurrency cryptoCurrency;
    FiatCurrency fiatCurrency;
    
    Double currentCryptoAmount;

    public MiddlewareCryptoValueChunk ( 
            Double cryptoAmount,
            Double fiatAmount,
            CryptoCurrency cryptoCurrency,
            FiatCurrency fiatCurrency)
    {
        this.cryptoAmount = cryptoAmount;
        this.fiatAmount = fiatAmount;
        this.cryptoCurrency = cryptoCurrency;
        this.fiatCurrency = fiatCurrency;
        
    }

    public Double getCryptoAmount() {
        return cryptoAmount;
    }

    public Double getFiatAmount() {
        return fiatAmount;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public FiatCurrency getFiatCurrency() {
        return fiatCurrency;
    }

    public void consumeCrypto (Double amount){

        currentCryptoAmount = currentCryptoAmount - amount;
        
        // Luis TODO: Verificar que no sea menor que cero, sino tirar excepcion.
    }    
    
    public Double getCurrentCryptoAmount() {
        return currentCryptoAmount;
    }
}
