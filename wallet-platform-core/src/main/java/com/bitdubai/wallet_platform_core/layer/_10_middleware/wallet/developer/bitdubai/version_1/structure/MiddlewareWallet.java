package com.bitdubai.wallet_platform_core.layer._10_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.wallet.CryptoAccount;
import com.bitdubai.wallet_platform_api.layer._10_middleware.wallet.FiatAccount;
import com.bitdubai.wallet_platform_api.layer._10_middleware.wallet.Wallet;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.FiatCurrency;

import java.util.List;

/**
 * Created by ciencias on 2/15/15.
 */
public class MiddlewareWallet implements Wallet {
    
    List<FiatAccount> fiatAccounts;
    List<CryptoAccount> cryptoAccounts;
   
    public FiatAccount createFiatAccount (FiatCurrency fiatCurrency){
        
        FiatAccount fiatAccount = new MiddlewareFiatAccount(fiatCurrency);
        
        this.fiatAccounts.add (fiatAccount);
        
        return fiatAccount;
    }

    public CryptoAccount createCryptoAccount (CryptoCurrency cryptoCurrency){
        
        CryptoAccount cryptoAccount = new MiddlewareCryptoAccount(cryptoCurrency);
        
        this.cryptoAccounts.add (cryptoAccount);
        
        return cryptoAccount;
    }

    public void removeFiatAccount (int index){
        this.fiatAccounts.remove(index);
    }

    public void removeCryptoAccount (int index){
        this.cryptoAccounts.remove(index);
    }
    
    public FiatAccount getFiatAccount (int index){
        return fiatAccounts.get(index);
    }

    public CryptoAccount getCryptoAccount (int index){
        return cryptoAccounts.get(index);
    }

    public void sizeOfFiatAccounts (){
        fiatAccounts.size();
    }

    public void sizeOfCryptoAccounts (){
        cryptoAccounts.size();
    }
    
    
    

    
}
