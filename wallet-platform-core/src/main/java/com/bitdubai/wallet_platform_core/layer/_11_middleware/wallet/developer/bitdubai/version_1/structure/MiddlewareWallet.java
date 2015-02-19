package com.bitdubai.wallet_platform_core.layer._11_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.wallet.CryptoAccount;
import com.bitdubai.wallet_platform_api.layer._10_middleware.wallet.FiatAccount;
import com.bitdubai.wallet_platform_api.layer._10_middleware.wallet.WalletManager;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.FiatCurrency;

import java.util.List;

/**
 * Created by ciencias on 2/15/15.
 */

/**
 * The Middleware Wallet manages the information about the funds the user have in a wallet. It has a multi-layer design.
 * 
 * The top one is the Account Layer, that is what users sees, their accounts.
 * 
 * The middle layer is the Crypto Value Layer, that is what the user has in crypto currencies.
 * 
 * The bottom layer is the Value Chunk Layer, that represents the chunks of crypto the user have, maintaining the
 * relationship with the price it was bought.
 * 
 * The wallet also manages inter account transactions.
 * 
 * * * * * *
 * * * 
 */

public class MiddlewareWallet implements WalletManager {
    
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
    
    public void transferFromFiatToFiat (FiatAccount fiatAccountFrom, FiatAccount fiatAccountTo, Double amountFrom, Double amountTo){
                
    }
    
    public void transferFromCryptoToCrypto (CryptoAccount cryptoAccountFrom, CryptoAccount cryptoAccountTo, Double amountFrom, Double amountTo){

    }

    public void transferFromFiatToCrypto (FiatAccount fiatAccountFrom, CryptoAccount cryptoAccountTo, Double amountFrom, Double amountTo){

    }

    public void transferFromCryptoToFiat (CryptoAccount cryptoAccountFrom, FiatAccount fiatAccountTo, Double amountFrom, Double amountTo){

    }
    
    public void debitFiatAccount (FiatAccount fiatAccount,Double amount){
        
    }

    public void creditFiatAccount (FiatAccount fiatAccount,Double amount){

    }

    public void debitCryptoAccount (CryptoAccount cryptoAccount,Double amount){

    }

    public void creditCryptoAccount (CryptoAccount cryptoAccount,Double amount){

    }
}
