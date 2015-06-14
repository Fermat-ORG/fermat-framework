package com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions;

/**
 * The Class <code>com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantUpdateWalletContactException</code>
 * is thrown when an error occurs trying to update any contact from a wallet
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantUpdateWalletContactException extends Exception {

    /**
     * Constructor
     */
    public CantUpdateWalletContactException(){
        super();
    }

    /**
     * Constructor whit parameter
     * @param msg
     */
    public CantUpdateWalletContactException(String msg){
        super(msg);
    }
}
