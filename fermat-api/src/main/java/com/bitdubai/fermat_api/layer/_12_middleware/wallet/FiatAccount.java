package com.bitdubai.fermat_api.layer._12_middleware.wallet;

import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;

/**
 * Created by ciencias on 2/15/15.
 */
public interface FiatAccount {

    public long getBalance() ;

    public FiatCurrency getFiatCurrency() ;

    public String getLabel() ;

    public String getName() ;

    public void setLabel(String label) ;

    public void setName(String name) ;

    public AccountStatus getStatus();

    public double availableBalance ();

    public void openAccount();
    
    public void closeAccount();
    
    public void deleteAccount();
}
