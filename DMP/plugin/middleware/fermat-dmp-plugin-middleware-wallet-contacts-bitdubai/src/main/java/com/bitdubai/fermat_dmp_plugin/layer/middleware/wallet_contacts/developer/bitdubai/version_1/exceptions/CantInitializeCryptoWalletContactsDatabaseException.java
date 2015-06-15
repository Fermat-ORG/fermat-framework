package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCryptoWalletContactsDatabaseException extends Exception {

    /**
     * Constructor
     */
    public CantInitializeCryptoWalletContactsDatabaseException(){
        super();
    }

    /**
     * Constructor whit parameter
     * @param msg
     */
    public CantInitializeCryptoWalletContactsDatabaseException(String msg){
        super(msg);
    }
}
