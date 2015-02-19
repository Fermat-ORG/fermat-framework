package com.bitdubai.wallet_platform_core.layer._11_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.wallet.FiatAccount;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.FiatCurrency;

/**
 * Created by ciencias on 2/15/15.
 */

public class MiddlewareFiatAccount implements FiatAccount  {
    
    Double balance;
    FiatCurrency fiatCurrency;
    String label;
    String name;
    
    public MiddlewareFiatAccount (FiatCurrency fiatCurrency){
        this.fiatCurrency = fiatCurrency;
    }

    public Double getBalance() {
        return balance;
    }

    public FiatCurrency getFiatCurrency() {
        return fiatCurrency;
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
    
    public double availableBalance (){
        
        // Luis: TODO: Calculate the available balance for this account.
        
        return 0;
    }
    
}
