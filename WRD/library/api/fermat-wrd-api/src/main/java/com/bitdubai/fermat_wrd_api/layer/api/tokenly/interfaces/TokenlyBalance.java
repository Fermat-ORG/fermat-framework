package com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces;

import com.bitdubai.fermat_wrd_api.all_definitions.enums.TokenlyBalancesType;
import com.bitdubai.fermat_wrd_api.all_definitions.enums.TokenlyCurrency;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public interface TokenlyBalance {

    /**
     * This method return the balance type
     * @return
     */
    TokenlyBalancesType getType();

    /**
     * This method returns the tokenly balance
     * @return
     */
    long getBalance();

    /**
     * This method returns the currency type.
     * @return
     */
    TokenlyCurrency getCurrencyType();

}
