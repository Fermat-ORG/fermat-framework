package com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions;

/**
 * The Class <code>com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantCreateWalletContactException</code>
 * is thrown when an error occurs trying to create a new contact for a wallet
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantCreateWalletContactException extends Exception {

    /**
     * Constructor
     */
    public CantCreateWalletContactException(){
        super();
    }

    /**
     * Constructor whit parameter
     * @param msg
     */
    public CantCreateWalletContactException(String msg){
        super(msg);
    }
}
