package com.bitdubai.fermat_api.layer._11_middleware.wallet;

import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;

/**
 * Created by ciencias on 2/15/15.
 */
public interface FiatAccount {

    public Double getBalance() ;

    public FiatCurrency getFiatCurrency() ;

    public String getLabel() ;

    public String getName() ;

    public void setLabel(String label) ;

    public void setName(String name) ;

    public double availableBalance ();
}
