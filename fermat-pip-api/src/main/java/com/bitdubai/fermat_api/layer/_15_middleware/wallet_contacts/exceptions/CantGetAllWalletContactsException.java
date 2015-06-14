package com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions;

/**
 * The Class <code>com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException</code>
 * is thrown when an error occurs trying to get all contacts from a wallet
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantGetAllWalletContactsException extends Exception {

    /**
     * Constructor
     */
    public CantGetAllWalletContactsException(){
        super();
    }

    /**
     * Constructor whit parameter
     * @param msg
     */
    public CantGetAllWalletContactsException(String msg){
        super(msg);
    }
}
