package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by nelson on 22/10/15.
 */
public class CantGetCryptoBrokerWalletException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Can't get a reference of CryptoBrokerWallet";

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantGetCryptoBrokerWalletException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Call CantGetCryptoBrokerWalletException(message, cause, "", "")
     *
     * @param message the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause   the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     */
    public CantGetCryptoBrokerWalletException(String message, Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Call CantGetCryptoBrokerWalletException(DEFAULT_MESSAGE, cause, "", "")
     *
     * @param cause the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     */
    public CantGetCryptoBrokerWalletException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, "", "");
    }
}