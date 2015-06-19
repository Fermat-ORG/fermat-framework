package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException</code>
 * is thrown when an error occurs trying to get a wallet contact registry
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantGetWalletContactRegistryException extends Exception {

    /**
     * Constructor
     */
    public CantGetWalletContactRegistryException(){
        super();
    }

    /**
     * Constructor whit parameter
     * @param msg as
     */
    public CantGetWalletContactRegistryException(String msg){
        super(msg);
    }
}
