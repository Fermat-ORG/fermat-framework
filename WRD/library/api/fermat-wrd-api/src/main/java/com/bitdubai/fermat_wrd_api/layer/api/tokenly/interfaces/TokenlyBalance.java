package com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public interface TokenlyBalance {

    String getType();

    long getBalance();

    String getCurrencyType();

}
