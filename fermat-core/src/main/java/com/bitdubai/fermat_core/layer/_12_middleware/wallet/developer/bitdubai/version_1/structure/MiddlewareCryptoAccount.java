package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.CryptoAccount;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;

import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */
public class MiddlewareCryptoAccount implements CryptoAccount{

    UUID id;
    String label ="";
    String name ="";
    long balance = 0;
    CryptoCurrency cryptoCurrency;
    

    public MiddlewareCryptoAccount (UUID id){
        this.id = id;
    }

    /**
     * MiddlewareFiatAccount interface implementation.
     */
    public void setBalance(Long balance){
        this.balance = balance;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }


    public void persistToMedia() {

    }
    
    /**
     * FiatAccount interface implementation.
     */
    public long getBalance() {
        return balance;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setName(String name) {
        this.name = name;
    }
}
